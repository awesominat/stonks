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
         * <p>
         * Info is fetched using the drivers.Finnhub.getCompanyProfile() method, which interacts with the API through
         *   an OKHTTP client.
         * The information in the CompanyInformation object generated by the driver when data is taken from the API is
         *   transferred into a HashMap through the GetInfoOutputData constructor.
         *
         * @param  getInfoInputData  an InputData object containing the ticker of the company whose profile we fetch
         */
        String ticker = getInfoInputData.getTicker();

        try {
            // Make API call
            CompanyInformation company_info = driverAPI.getCompanyProfile(ticker);

            // Save API output using OutputData format for the GetNews use case
            GetInfoOutputData result = new GetInfoOutputData(ticker, company_info);

            getInfoPresenter.prepareSuccessView(result);

        } catch (RuntimeException e) {
//            e.printStackTrace();
            getInfoPresenter.prepareFailView("API call failed.");
        }
    }
}