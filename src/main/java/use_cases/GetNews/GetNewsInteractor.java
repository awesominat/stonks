package main.java.use_cases.GetNews;

import main.java.entities.CompanyNews;
import main.java.use_cases.APIAccessInterface;

import java.time.LocalDate;
import java.util.List;

public class GetNewsInteractor implements GetNewsInputBoundary {
    GetNewsOutputBoundary getNewsPresenter;
    APIAccessInterface driverAPI;

    public GetNewsInteractor(GetNewsOutputBoundary getNewsPresenter, APIAccessInterface driverAPI) {
        this.getNewsPresenter = getNewsPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute(GetNewsInputData getNewsInputData) {
        /**
        * The getNewsInputData parameter should follow the specifications laid out in that class.
        * <p>
        * This method implements the bulk of the GetNews use case.
        * News is fetched over a time period of a month prior to the method call.
        *
        * @param  getNewsInputData  an InputData object following the relevant CA Engine rules
        */
        String ticker = getNewsInputData.getTicker();

        // Define end of news period to be right now
        LocalDate to = LocalDate.now();
        // Define start of news period to be a month ago
        LocalDate from = to.minusMonths(1);

        try {
            // Make API call
            List<CompanyNews> company_news_list = driverAPI.getCompanyNews(ticker, from, to);

            // Save API output using OutputData format for the GetNews use case
            GetNewsOutputData result = new GetNewsOutputData(ticker, company_news_list);

            getNewsPresenter.prepareSuccessView(result);

        } catch (RuntimeException e) {
            e.printStackTrace();
            getNewsPresenter.prepareFailView("something happened");
        }
    }
}
