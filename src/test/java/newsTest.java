import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import drivers.Finnhub;
import entities.*;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import use_cases.APIAccessInterface;
import use_cases.GetNews.GetNewsOutputBoundary;
import use_cases.GetNews.GetNewsDataAccessInterface;
import use_cases.GetNews.GetNewsInputData;
import use_cases.GetNews.GetNewsInteractor;
import use_cases.GetNews.GetNewsOutputData;

public class newsTest {

    @Test
    public void testGetNews() throws IOException {
        APIAccessInterface mockApi = Mockito.mock(APIAccessInterface.class);
        GetNewsDataAccessInterface userDataAccessObject = Mockito.mock(GetNewsDataAccessInterface.class);

        Finnhub fh = new Finnhub();
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusMonths(1);
        List<CompanyNews> news_list = fh.getCompanyNews("MSFT", from, to);
        CompanyNews first_news_item = news_list.get(1);  // Get second news item, just to be different.
        // Inspect news item
        System.out.println(first_news_item.toString());

        GetNewsInputData getNewsInputData = new GetNewsInputData("MSFT");
        GetNewsOutputBoundary mockGetNewsPresenter = Mockito.mock(GetNewsOutputBoundary.class);
        GetNewsInteractor getNewsInteractor = new GetNewsInteractor(mockGetNewsPresenter, mockApi);
        getNewsInteractor.execute(getNewsInputData);

        Mockito.verify(mockApi).getCompanyNews("MSFT", from, to);
        Mockito.verify(mockGetNewsPresenter).prepareSuccessView(any(GetNewsOutputData.class));

//        List<PricePoint> lastMonthPrices = new ArrayList<>();
//        lastMonthPrices.add(new PricePoint(LocalDate.now(), 100.0));

//        Mockito.when(userDataAccessObject.get()).thenReturn(new CommonUser());
//        Mockito.when(mockApi.getCurrentPrice("AAPL")).thenReturn(new PricePoint(LocalDate.now(), 100.0));
//        Mockito.when(mockApi.getCompanyProfile("AAPL")).thenReturn(new CompanyInformation("US",
//                "Apple Inc", "AAPL", "https://www.apple.com/", "1980-12-12"));
//        Mockito.when(mockApi.getLastMonthPrices("AAPL")).thenReturn(lastMonthPrices);

        // Test API I guess
//        Mockito.when(mockApi.getCompanyNews("AAPL")).thenReturn(new CompanyNews("category", "2023-11-21", "headline", "url", "summary"));

//        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

//        Mockito.verify(userDataAccessObject).save();

//        User capturedUser = userDataAccessObject.get(); //userCaptor.getValue();

//        assert capturedUser.getBalance().equals(9000.0);
    }

}
