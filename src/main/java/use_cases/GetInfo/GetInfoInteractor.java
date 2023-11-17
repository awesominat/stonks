package use_cases.GetInfo;

import entities.CompanyInformation;
import use_cases.APIAccessInterface;

public class GetInfoInteractor implements GetInfoInputBoundary {
    GetInfoOutputBoundary getInfoPresenter;
    APIAccessInterface driverAPI;

    public GetInfoInteractor(GetInfoOutputBoundary getInfoPresenter, APIAccessInterface driverAPI) {
        this.getInfoPresenter = getInfoPresenter;
        this.driverAPI = driverAPI;
    }

    @Override
    public void execute(GetInfoInputData getInfoInputData) {
        /**
         * The getInfoInputData parameter should follow the specifications laid out in that class.
         * <p>
         * This method implements the bulk of the GetInfo use case.
         * Info is fetched using the Finnhub driver.
         *
         * @param  getInfoInputData  an InputData object following the relevant CA Engine rules
         */
        String ticker = getInfoInputData.getTicker();

        try {
            // Make API call
            CompanyInformation company_info = driverAPI.getCompanyProfile(ticker);

            // Save API output using OutputData format for the GetNews use case
            GetInfoOutputData result = new GetInfoOutputData(ticker, company_info);

            getInfoPresenter.prepareSuccessView(result);

        } catch (RuntimeException e) {
            e.printStackTrace();
            getInfoPresenter.prepareFailView("something happened");
        }
    }
}
