package use_case.Buy;

/**
 * Defines the input boundary for the Buy use case.
 */
public interface BuyInputBoundary {

    /**
     * @param buyInputData The input data for the Buy use case.
     */
    void execute(BuyInputData buyInputData);

}
