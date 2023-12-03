package interface_adapter.Buy;

import use_case.Buy.BuyInputBoundary;
import use_case.Buy.BuyInputData;

/**
 * Handles the user's input and passing it to the interactor.
 */
public class BuyController {
    final private BuyInputBoundary buyInteractor;

    /**
     * @param buyInteractor The interactor to be used for the execution of the use case.
     */
    public BuyController(BuyInputBoundary buyInteractor) {
        this.buyInteractor = buyInteractor;
    }

    public void execute(String amount, String ticker) {
        try {
            BuyInputData buyInputData = new BuyInputData(Double.parseDouble(amount), ticker);
            buyInteractor.execute(buyInputData);
        } catch (NumberFormatException ex) {
            BuyInputData buyInputData = new BuyInputData(-1.0, ticker);
            buyInteractor.execute(buyInputData);
        }
    }

    public void execute(String ticker) {
        BuyInputData buyInputData = new BuyInputData(ticker);
        buyInteractor.execute(buyInputData);
    }
    public void execute() {
        BuyInputData buyInputData = new BuyInputData();
        buyInteractor.execute(buyInputData);
    }
}
