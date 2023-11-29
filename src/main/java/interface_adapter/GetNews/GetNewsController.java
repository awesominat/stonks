package interface_adapter.GetNews;

import use_case.GetNews.GetNewsInputBoundary;
import use_case.GetNews.GetNewsInputData;

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
