package use_cases.GetInfo;

import entities.CompanyInformation;

import java.util.Map;
import java.util.HashMap;

public class GetInfoOutputData {
    String ticker;
    Map<String, String> stringMap;

    public GetInfoOutputData(String ticker, CompanyInformation companyInformation) {
        this.ticker = ticker;

        // Initialize stringMap attribute, then manually put each attribute to the Map from the CompanyInformation object
        this.stringMap = new HashMap<String, String>();
        stringMap.put("country", companyInformation.getCountry());
        stringMap.put("name", companyInformation.getName());
        stringMap.put("ticker", companyInformation.getTicker());
        stringMap.put("weburl", companyInformation.getWeburl());
        stringMap.put("ipo", companyInformation.getIpo());
    }

    public String getTicker() {return ticker;}

    public Map<String, String> getInfo() {return stringMap;}
}
