package interface_adapter;

import entity.*;
import interface_adapter.GetNews.GetNewsController;
import interface_adapter.GetNews.GetNewsPresenter;
import interface_adapter.GetNews.GetNewsState;
import interface_adapter.GetNews.GetNewsViewModel;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.APIAccessInterface;
import use_case.GetNews.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetNewsInterfaceAdapterTest {
    private APIAccessInterface mockAPI;
    private GetNewsOutputBoundary presenter;
    private GetNewsInteractor interactor;

    @BeforeAll
    public void setUpAPI() throws RuntimeException, APIAccessInterface.TickerNotFoundException {
        mockAPI = Mockito.mock(APIAccessInterface.class);

        // Setup mock output for "AAPL" get news call.
        List<CompanyNews> newsOut = new ArrayList<>();
        // Add five duplicate articles to output List.
        for (int i = 0; i < 5; i++) {
            newsOut.add(new CompanyNews(
                            "company",
                            LocalDate.parse("2023-11-28").atStartOfDay(),
                            "The Cash-Rich Magnificent 7 For The Long Haul",
                            "https://finnhub.io/api/news?id=69513fc7f9f8ea2f36c36a0cd322ff4acd6983bf7dbb70d44fcc15fb475810ac",
                            "Wage growth, low unemployment rates, and artificial intelligence are driving the thriving U.S. economy. Click here to read my most recent analysis."
                    )
            );
        }

        // Setup parameters for mock call.
        LocalDate now = LocalDate.now();
        LocalDate monthAgo = now.minusMonths(1);

        // Simulate successful mock call.
        Mockito.when(mockAPI.getCompanyNews("AAPL", monthAgo, now)).thenReturn(newsOut);

        // Simulate ticker error.
        Mockito.when(mockAPI.getCompanyNews("SSS", monthAgo, now)).thenThrow(
                new APIAccessInterface.TickerNotFoundException("Ticker SSS not found. Please enter a valid ticker.")
        );

        // Simulate API failure error.
        Mockito.when(mockAPI.getCompanyNews("API does not respond", monthAgo, now)).thenThrow(
                new RuntimeException("API did not respond.")
        );
    }

    @BeforeEach
    public void setUpPresenter() {
        presenter = Mockito.mock(GetNewsOutputBoundary.class);
        interactor = new GetNewsInteractor(presenter, mockAPI);
    }

    @AfterEach
    public void tearDown() {
        Mockito.clearInvocations(mockAPI);
    }

    @Test
    public void testInfoFetch() {
        GetNewsController controller = new GetNewsController(interactor);
        controller.execute("AAPL");

        ArgumentCaptor<GetNewsOutputData> captor = ArgumentCaptor.forClass(GetNewsOutputData.class);

        Mockito.verify(presenter).prepareSuccessView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareFailView(any(String.class));

        GetNewsOutputData response = captor.getValue();

        Map<String, String> newsItem = response.getNewsItems().get(0);

        assertEquals(5, response.getNewsItems().size());
        assertEquals("AAPL", response.getTicker());
        assertEquals("The Cash-Rich Magnificent 7 For The Long Haul", newsItem.get("headline"));
        assertEquals("company", newsItem.get("category"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.parse(newsItem.get("datetime"), formatter);
        String date = localDateTime.format(formatter);
        assertEquals("2023-11-28", localDateTime.toLocalDate().toString());
    }

    @Test
    public void testInterfaceAdapters() {
        // Test-specific setup.
        GetNewsController controller = new GetNewsController(interactor);
        controller.execute("AAPL");
        ArgumentCaptor<GetNewsOutputData> captor = ArgumentCaptor.forClass(GetNewsOutputData.class);
        Mockito.verify(presenter).prepareSuccessView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareFailView(any(String.class));
        GetNewsOutputData response = captor.getValue();

        // Test Interface Adapters
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GetNewsViewModel getNewsViewModel = new GetNewsViewModel();
        GetNewsPresenter getNewsPresenter = new GetNewsPresenter(viewManagerModel, getNewsViewModel);

        getNewsPresenter.prepareSuccessView(response);

        // Verify properties of the state
        GetNewsState state = getNewsViewModel.getState();

//        assertFalse(state.getRenderNewInfo());
        assertNull(state.getTickerError());

        Map<String, String> newsItem = state.getNewsItems().get(0);
        assertEquals(5, state.getNewsItems().size());
        assertEquals("AAPL", state.getTicker());
        assertEquals("The Cash-Rich Magnificent 7 For The Long Haul", newsItem.get("headline"));
        assertEquals("company", newsItem.get("category"));
    }

    @Test
    public void testInvalidTicker() throws APIAccessInterface.TickerNotFoundException {
        GetNewsController controller = new GetNewsController(interactor);
        controller.execute("SSS");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(presenter).prepareFailView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareSuccessView(any(GetNewsOutputData.class));

        String tickerErrorMessage = captor.getValue();

        assertEquals("Ticker 'SSS' not found. Please enter a valid ticker.", tickerErrorMessage);
    }

    @Test
    public void testNoApiResponse() throws RuntimeException {
        GetNewsController controller = new GetNewsController(interactor);
        controller.execute("API does not respond");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(presenter).prepareFailView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareSuccessView(any(GetNewsOutputData.class));

        String apiErrorMessage = captor.getValue();

        assertEquals("API did not respond.", apiErrorMessage);
    }
}
