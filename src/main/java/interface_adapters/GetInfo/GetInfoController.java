package interface_adapters.GetInfo;

import use_cases.GetInfo.GetInfoInputBoundary;
import use_cases.GetInfo.GetInfoInputData;

public class GetInfoController {

    final private GetInfoInputBoundary getInfoInteractor;

    public GetInfoController(GetInfoInputBoundary getInfoInteractor) {
        this.getInfoInteractor = getInfoInteractor;
    }

    public void execute(String ticker) {
        GetInfoInputData getInfoInputData = new GetInfoInputData(ticker);
        getInfoInteractor.execute(getInfoInputData);
    }

}
