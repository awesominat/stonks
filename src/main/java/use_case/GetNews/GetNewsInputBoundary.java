package use_case.GetNews;

/**
 * Define the input boundary for the GetNews use case.
 * Declare a method to execute the use case with the provided input data.
 */
public interface GetNewsInputBoundary {

    /**
     * Execute the GetNews use case with the given input data.
     *
     * @param getNewsInputData The input data for the "GetNews" use case.
     */
    void execute(GetNewsInputData getNewsInputData);

}
