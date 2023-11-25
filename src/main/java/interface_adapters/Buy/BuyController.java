package interface_adapters.Buy;

import use_cases.Buy.BuyInputBoundary;
import use_cases.Buy.BuyInputData;

public class BuyController {
    final private BuyInputBoundary buyInteractor;

    public BuyController(BuyInputBoundary buyInteractor) {
        this.buyInteractor = buyInteractor;
    }

    public void execute(String amount, String ticker) {
        try {
            BuyInputData buyInputData = new BuyInputData(Double.parseDouble(amount), ticker);
            buyInteractor.execute(buyInputData);
        } catch (NumberFormatException ex) {
            BuyInputData buyInputData = new BuyInputData(ticker);
            buyInteractor.execute(buyInputData);
        }
    }
}
