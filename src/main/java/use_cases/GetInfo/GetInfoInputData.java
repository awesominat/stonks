package main.java.use_cases.GetInfo;

public class GetInfoInputData {
    public String ticker;

    public GetInfoInputData(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
