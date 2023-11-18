package interface_adapters.Sell;

import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInputData;

public class SellController {
    final private SellInputBoundary sellInteractor;

    public SellController(SellInputBoundary sellInteractor) {
        this.sellInteractor = sellInteractor;
    }

    public void execute(String amount, String ticker) {
        SellInputData sellInputData = new SellInputData(amount, ticker, username);
        sellInteractor.execute(sellInputData);
    }
}
