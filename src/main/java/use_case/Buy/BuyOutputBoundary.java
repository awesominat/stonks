package use_case.Buy;

/**
 * This interface is used to prepare the data for the view.
 */
public interface BuyOutputBoundary {

    /**
     * Prepares the data for the view when a successful purchase is made.
     *
     * @param result The result of the use case.
     */
    void prepareSuccessView(BuyOutputData result);

    /**
     * Prepares the data for the view when a successful search is made.
     *
     * @param result The result of the use case.
     */
    void prepareSuccessView(BuySearchOutputData result);

    /**
     * Prepares the data for the view when the use case fails.
     * The use case would fail because of one of two reasons:
     *   (1) the user does not have enough money to make their purchase, or
     *   (2) the user entered an invalid ticker.
     *
     * @param error The error message.
     */
    void prepareFailView(String error);

}