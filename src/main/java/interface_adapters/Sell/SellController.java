package interface_adapters.Sell;

import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInputData;

public class SellController {
    final private SellInputBoundary sellInteractor;

    public SellController(SellInputBoundary sellInteractor) {
        this.sellInteractor = sellInteractor;
    }

    public void execute(Double amount, String ticker, String username) {
        SellInputData sellInputData = new SellInputData(amount, ticker, username);
        sellInteractor.execute(sellInputData);
    }
}
