package use_cases.GetNews;

public class GetNewsInputData {
    public String ticker;

    public GetNewsInputData(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
