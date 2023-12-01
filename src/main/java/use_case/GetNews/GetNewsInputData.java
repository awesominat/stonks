package use_case.GetNews;

/**
 * Represents the input data for the GetNews use case.
 * Encapsulates the necessary information required to fetch news for a specific company.
 */
public class GetNewsInputData {

    /**
     * The ticker tied to the company for which news is to be fetched.
     */
    public String ticker;

    /**
     * Constructs a new GetNewsInputData object with the specified stock ticker.
     *
     * @param ticker The ticker symbol of the company
     */
    public GetNewsInputData(String ticker) {
        this.ticker = ticker;
    }

    /**
     * Gets the ticker symbol passed to this InputData object associated with a stock.
     *
     * @return the ticker symbol passed when this object was created.
     */
    public String getTicker() {
        return ticker;
    }

}
