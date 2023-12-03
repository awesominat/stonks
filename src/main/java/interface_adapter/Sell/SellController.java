package interface_adapter.Sell;

import use_case.Sell.SellInputBoundary;
import use_case.Sell.SellInputData;

public class SellController {
    final private SellInputBoundary sellInteractor;

    /**
     * Initializes a sell controller
     *
     * @param sellInteractor    interactor for the sell use case
     */
    public SellController(SellInputBoundary sellInteractor) {
        this.sellInteractor = sellInteractor;
    }

    /**
     * The first execute method of this interactor. This is called when the user hits the sell stocks
     * button from the sell view.
     * Checks that the sell amount is a decimal value, otherwise passes -1.0 in the input data, which
     * will eventually trigger a popup stating the incorrect input format.
     *
     * @param amount    amount of stocks that need to be sold
     * @param ticker    ticker of the stock that is being sold
     */
    public void execute(String amount, String ticker) {
        try {
            SellInputData sellInputData = new SellInputData(Double.parseDouble(amount), ticker);
            sellInteractor.execute(sellInputData);
        } catch (NumberFormatException ex) {
            SellInputData sellInputData = new SellInputData(-1.0, ticker);
            sellInteractor.execute(sellInputData);
        }
    }

    /**
     * The second execute function. This is called when the use navigates onto the sell screen
     * and NOT when the sell stocks button is pressed. This allows for the sell stocks table
     * to populate with updated information about which stocks the use owns and grabs the current prices
     * of those stocks from the DashboardState (in the presenter).
     *
     * There is no input data required since this is essentially a refresh call, so it calls the second
     * initializer for the sell input data, which sets the executeTypeSell attribute to false.
     */
    public void execute() {
        SellInputData sellInputData = new SellInputData();
        sellInteractor.execute(sellInputData);
    }


}
