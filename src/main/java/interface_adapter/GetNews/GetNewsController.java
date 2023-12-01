package interface_adapter.GetNews;

import use_case.GetNews.GetNewsInputBoundary;
import use_case.GetNews.GetNewsInputData;

/**
 * Serves as the controller for the GetNews use case.
 * Facilitates the execution of the use case by communicating with the associated
 * GetNewsInteractor through the GetNewsInputBoundary interface.
 */
public class GetNewsController {
    final private GetNewsInputBoundary getNewsInteractor;

    /**
     * Constructs a new GetNewsController with the specified GetNewsInputBoundary.
     *
     * @param getNewsInteractor  The interactor responsible for executing the GetNews use case.
     */
    public GetNewsController(GetNewsInputBoundary getNewsInteractor) {
        this.getNewsInteractor = getNewsInteractor;
    }

    /**
     * Initiates the execution of the GetNews use case by creating an input data object based on the provided ticker
     * and passing that input data object into the execute method of the interactor for this use case,
     * which is an attribute of this controller.
     *
     * @param ticker  The ticker of the company for which news is to be retrieved.
     */
    public void execute(String ticker) {
        GetNewsInputData getNewsInputData = new GetNewsInputData(ticker);
        getNewsInteractor.execute(getNewsInputData);
    }
}
