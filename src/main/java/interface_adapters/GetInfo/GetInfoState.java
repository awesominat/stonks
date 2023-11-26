package interface_adapters.GetInfo;

public class GetInfoState {

    private String ticker = null;
    private String tickerError = null;

    public GetInfoState(String ticker, String tickerError) {
        this.ticker = ticker;
        this.tickerError = tickerError;
    }

    public void setTickerError(String tickerError) {this.tickerError = tickerError;}

    public String getTickerError() {return tickerError;}

    public void setTicker(String ticker) {this.ticker = ticker;}

    public String getTicker() {return ticker;}

    // Due to call without parameters in GetInfoViewModel, we need to explicitly define the default constructor
    //  by overloading it.
    public GetInfoState() {}

}
