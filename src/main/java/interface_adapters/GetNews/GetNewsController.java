package interface_adapters.GetNews;

import use_cases.GetNews.GetNewsInputBoundary;
import use_cases.GetNews.GetNewsInputData;

public class GetNewsController {
    final private GetNewsInputBoundary getNewsInteractor;

    public GetNewsController(GetNewsInputBoundary getNewsInteractor) {
        this.getNewsInteractor = getNewsInteractor;
    }

    public void execute(String ticker) {
        GetNewsInputData getNewsInputData = new GetNewsInputData(ticker);
        getNewsInteractor.execute(getNewsInputData);
    }
}
