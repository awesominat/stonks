package use_case.GetNews;

/**
 * Defines the input boundary for this use case (GetNews).
 * Declares method to execute this use case with the provided input data.
 */
public interface GetNewsInputBoundary {

    /**
     * Executes the GetNews use case with the given input data.
     *
     * @param getNewsInputData The input data for the GetNews use case.
     */
    void execute(GetNewsInputData getNewsInputData);

}
