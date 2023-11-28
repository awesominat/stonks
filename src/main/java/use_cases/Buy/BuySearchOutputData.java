package use_cases.Buy;

import entities.CompanyInformation;

import java.util.HashMap;
import java.util.Map;

public class BuySearchOutputData {
    private String ticker;
    private Map<String, String> stringMap;
    private Double curBalance;
    private Double currentlyHeld;

    public BuySearchOutputData(
            String ticker,
            CompanyInformation companyInformation,
            Double currentPrice,
            Double currentlyHeld,
            Double curBalance
    ) {
        this.ticker = ticker;
        this.curBalance = curBalance;

        this.stringMap = new HashMap<>();
        stringMap.put("country", companyInformation.getCountry());
        stringMap.put("name", companyInformation.getName());
        stringMap.put("ticker", companyInformation.getTicker());
        stringMap.put("weburl", companyInformation.getWeburl());
        stringMap.put("ipo", companyInformation.getIpo());
        stringMap.put("price", String.valueOf(currentPrice));
        stringMap.put("currently held", String.valueOf(currentlyHeld));
    }

    public String getTicker() {
        return ticker;
    }

    public Double getCurBalance() {
        return curBalance;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }
}
