package interface_adapters.Buy;

import use_cases.Buy.BuyInputBoundary;
import use_cases.Buy.BuyInputData;

public class BuyController {
    final private BuyInputBoundary buyInteractor;

    public BuyController(BuyInputBoundary buyInteractor) {
        this.buyInteractor = buyInteractor;
    }

    public void execute(Double amount, String ticker, String username) {
        // TODO Ricky
        BuyInputData buyInputData = new BuyInputData(amount, ticker, username);
        buyInteractor.execute(buyInputData);
    }
}
