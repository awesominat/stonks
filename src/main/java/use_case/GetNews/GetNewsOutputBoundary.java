package use_case.GetNews;

/**
 * Defines the output boundary for the GetNews use case.
 * Declares methods to prepare views for success and failure scenarios.
 */
public interface GetNewsOutputBoundary {

    /**
     * Prepares the view to represent a successful execution of the GetNews use case.
     *
     * @param result  The output data containing information for this success scenario.
     */
    void prepareSuccessView(GetNewsOutputData result);

    /**
     * Prepares the view to represent a failed execution of the GetNews use case.
     *
     * @param error  A string representing the error information for the failure scenario.
     */
    void prepareFailView(String error);

}
