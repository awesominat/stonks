package use_case.GetNews;

import entity.CompanyNews;
import use_case.APIAccessInterface;

import java.time.LocalDate;
import java.util.List;

/**
 * Implements the {@code GetNewsInputBoundary} interface.
 * Represents the interactor for the {@code GetNews} use case.
 * Implements input boundary's execute method to fetch news articles related to the company specified (in input data).
 */
public class GetNewsInteractor implements GetNewsInputBoundary {

    /** The presenter responsible for handling the output of the GetNews use case. */
    GetNewsOutputBoundary getNewsPresenter;

    /** The API access interface used for fetching news data from an external source. */
    APIAccessInterface driverAPI;

    /**
     * Constructs a new GetNewsInteractor with the specified presenter and API access interface.
     *
     * @param getNewsPresenter The presenter for handling the output of the GetNews use case.
     * @param driverAPI The API access interface used for fetching news data from the API.
     */
    public GetNewsInteractor(
            GetNewsOutputBoundary getNewsPresenter,
            APIAccessInterface driverAPI
    ) {
        this.getNewsPresenter = getNewsPresenter;
        this.driverAPI = driverAPI;
    }

    /**
     * Executes GetNews use case based on provided input data.
     * News fetched over time period of up to one month prior to the method call.
     *
     * @param getNewsInputData An input data object for this use case.
     */
    @Override
    public void execute(GetNewsInputData getNewsInputData) {
        String ticker = getNewsInputData.getTicker();

        // Define end of news period to be right now
        LocalDate to = LocalDate.now();
        // Define start of news period to be a month ago
        LocalDate from = to.minusMonths(1);

        try {
            // Make API call to retrieve news related to the specified company.
            List<CompanyNews> companyNewsList = driverAPI.getCompanyNews(ticker, from, to);

            // Save API output using OutputData format for the GetNews use case.
            GetNewsOutputData result = new GetNewsOutputData(ticker, companyNewsList);

            getNewsPresenter.prepareSuccessView(result);

        } catch (RuntimeException e) {
            // Handle any runtime exception and prepare a fail view.
            getNewsPresenter.prepareFailView("API did not respond.");
          
        } catch (APIAccessInterface.TickerNotFoundException e) {
            getNewsPresenter.prepareFailView(
                    String.format("Ticker '%s' not found. Please enter a valid ticker.", ticker)
            );
        }
    }

}
