package use_case.GetNews;

import entity.CompanyNews;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GetNewsOutputData {
    String ticker;
    List<Map<String, String>> newsItems;

    /**
     * Constructs a new GetNewsOutputData instance with the specified ticker symbol
     * and a list of CompanyNews items.
     *
     * @param ticker The ticker symbol associated with the company whose news was retrieved by this use case.
     * @param companyNewsItems A list of CompanyNews items, each containing information for a news article.
     */
    public GetNewsOutputData(String ticker, List<CompanyNews> companyNewsItems) {

        this.ticker = ticker;
        this.newsItems = new ArrayList<Map<String, String>>();

        for (CompanyNews companyNews : companyNewsItems) {
            Map<String, String> newsItem = new HashMap<>();

            newsItem.put("category", companyNews.getCategory());
            newsItem.put("datetime", companyNews.getDatetime().toString());
            newsItem.put("headline", companyNews.getHeadline());
            newsItem.put("url", companyNews.getUrl());
            newsItem.put("summary", companyNews.getSummary());

            this.newsItems.add(newsItem);
        }

    }

    public String getTicker() {return ticker;}

    public List<Map<String, String>> getNewsItems() {return newsItems;}

}
