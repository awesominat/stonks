package interface_adapters.Sell;

import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInputData;

public class SellController {
    final private SellInputBoundary sellInteractor;

    public SellController(SellInputBoundary sellInteractor) {
        this.sellInteractor = sellInteractor;
    }

    public void execute(String amount, String ticker) {
        try {
            SellInputData sellInputData = new SellInputData(Double.parseDouble(amount), ticker);
            sellInteractor.execute(sellInputData);
        } catch (NumberFormatException ex) {
            SellInputData sellInputData = new SellInputData(-1.0, ticker);
            sellInteractor.execute(sellInputData);
        }
    }
}
