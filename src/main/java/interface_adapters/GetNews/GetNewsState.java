package interface_adapters.GetNews;

public class GetNewsState {
    private String tickerError = null;
    private String ticker = null;

    public GetNewsState(String ticker, String tickerError) {
        this.ticker = ticker;
        this.tickerError = tickerError;
    }

    public String getTickerError() {return tickerError;}

    public void setTickerError(String tickerError) {this.tickerError = tickerError;}

    public String getTicker() {return ticker;}

    public void setTicker(String ticker) {this.ticker = ticker;}

    // Due to the copy constructor, the default constructor must be explicit. So we overload it.
    public GetNewsState() {}
}
