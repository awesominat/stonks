package use_case.GetNews;

import entity.CompanyNews;
import use_case.APIAccessInterface;

import java.time.LocalDate;
import java.util.List;

/**
 * Implements the GetNewsInputBoundary interface.
 * Represents the interactor for the GetNews use case.
 * Implements execute method to fetch news articles related to the specific company (specified in the input data).
 */
public class GetNewsInteractor implements GetNewsInputBoundary {
    GetNewsOutputBoundary getNewsPresenter;
    APIAccessInterface driverAPI;

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
     * @param getNewsInputData An InputData object following the relevant CA Engine rules
     */
    @Override
    public void execute(GetNewsInputData getNewsInputData) {
        String ticker = getNewsInputData.getTicker();

        // Define end of news period to be right now
        LocalDate to = LocalDate.now();
        // Define start of news period to be a month ago
        LocalDate from = to.minusMonths(1);

        try {
            // Make API call
            List<CompanyNews> companyNewsList = driverAPI.getCompanyNews(ticker, from, to);

            // Save API output using OutputData format for the GetNews use case
            GetNewsOutputData result = new GetNewsOutputData(ticker, companyNewsList);

            getNewsPresenter.prepareSuccessView(result);

        } catch (RuntimeException e) {
//            e.printStackTrace();
            getNewsPresenter.prepareFailView("Ticker not found or API did not respond.");
        }
    }
}
