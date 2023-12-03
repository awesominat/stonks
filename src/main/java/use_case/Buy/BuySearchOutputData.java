package use_case.Buy;

import entity.CompanyInformation;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the output data of the Buy use case when a ticker is searched.
 * This class is in contrast to BuyOutputData, when the execution of the use case indicates a purchase was attempted.
 */
public class BuySearchOutputData {
    private String ticker;
    private Map<String, String> stringMap;
    private Double curBalance;
    private Double currentlyHeld;

    /**
     * @param ticker The ticker of the company that was searched.
     * @param companyInformation The object encapsulating the company information of the company that was searched.
     * @param currentPrice The current price of the stock whose ticker was searched.
     * @param currentlyHeld The amount of shares of the company the user searched that the user currently holds.
     * @param curBalance The current balance of the user.
     */
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

    /**
     * @return The ticker of the company that was searched.
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @return The amount of shares of the company the user searched that the user currently holds.
     */
    public Double getCurBalance() {
        return curBalance;
    }

    /**
     * @return A map of the company information of the company that was searched.
     */
    public Map<String, String> getStringMap() {
        return stringMap;
    }
}
