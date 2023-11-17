package use_cases.GetInfo;

import entities.CompanyInformation;

import java.util.Map;
import java.util.HashMap;

public class GetInfoOutputData {
    String ticker;
    Map<String, String> info_map;

    public GetInfoOutputData(String ticker, CompanyInformation company_info) {
        this.ticker = ticker;

        // Initialize info_map attribute, then manually put each attribute to the Map from the CompanyInformation object
        this.info_map = new HashMap<String, String>();
        info_map.put("country", company_info.getCountry());
        info_map.put("name", company_info.getName());
        info_map.put("ticker", company_info.getTicker());
        info_map.put("weburl", company_info.getWeburl());
        info_map.put("ipo", company_info.getIpo());
    }

    public String getTicker() {return ticker;}

    public Map<String, String> getInfo() {return info_map;}
}
