package use_case.Buy;

/**
 * This class represents the input data for the BuyInteractor.
 */
public class BuyInputData {
    private final Double amount;
    private final String ticker;

    /**
     * Constructs a new BuyInputData object for buying a specified amount of stock.
     *
     * @param amount The amount of the stock to be purchased.
     * @param ticker The ticker symbol of the stock to be purchased.
     */
    public BuyInputData(Double amount, String ticker) {
        this.amount = amount;
        this.ticker = ticker;
    }

    /**
     * Constructs a new BuyInputData object for looking up information about a stock.
     *
     * @param ticker The ticker symbol of the stock for which information is needed.
     */
    public BuyInputData(String ticker) {
        this.amount = null;
        this.ticker = ticker;
    }

    /**
     * Constructs a new BuyInputData object without any initial stock purchase or lookup.
     * This can be used for operations like returning the user's current balance.
     */
    public BuyInputData() {
        this.amount = null;
        this.ticker = null;
    }

    /**
     * @return  The amount of the stock to be purchased.
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @return  The ticker symbol of the stock to be purchased.
     */
    public String getTicker() {
        return ticker;
    }

}
