package use_cases.GetNews;

import entities.CompanyNews;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GetNewsOutputData {
    String ticker;
    List<Map<String, String>> news_items;

    public GetNewsOutputData(String ticker, List<CompanyNews> company_news_items) {
        this.ticker = ticker;
        this.news_items = new ArrayList<Map<String, String>>();

        for (CompanyNews company_news : company_news_items) {
            Map<String, String> news_item = new HashMap<>();

            news_item.put("category", company_news.getCategory());
            news_item.put("datetime", company_news.getDatetime().toString());
            news_item.put("headline", company_news.getHeadline());
            news_item.put("url", company_news.getUrl());
            news_item.put("summary", company_news.getSummary());

            this.news_items.add(news_item);
        }
    }

    public String getTicker() {return ticker;}

    public List<Map<String, String>> getNewsItems() {return news_items;}

    // TODO decide whether this is necessary
    // public Map<String, String> getNewsItem(int idx) {return news_items.get(idx);}
}