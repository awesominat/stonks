package use_case.GetNews;

/**
 * The GetNewsInputData class represents the input data for the GetNews use case.
 * It encapsulates the necessary information required to fetch news for a specific company.
 */
public class GetNewsInputData {

    /**
     * The ticker symbol of the company for which news is to be fetched.
     */
    public String ticker;

    /**
     * Constructs a new GetNewsInputData object with the specified ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     */
    public GetNewsInputData(String ticker) {
        this.ticker = ticker;
    }

    /**
     * Gets the ticker symbol passed to this InputData object associated with a stock.
     *
     * @return the ticker symbol
     */
    public String getTicker() {
        return ticker;
    }

}
