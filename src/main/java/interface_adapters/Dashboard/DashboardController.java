package interface_adapters.Dashboard;

import use_cases.Sell.SellInputBoundary;
import use_cases.Sell.SellInputData;

public class DashboardController {
    final private SellInputBoundary sellInteractor;

    public DashboardController(SellInputBoundary sellInteractor) {
        this.sellInteractor = sellInteractor;
    }

    public void execute(Double amount, String ticker, String username) {
        SellInputData sellInputData = new SellInputData(amount, ticker, username);
        sellInteractor.execute(sellInputData);
    }
}
