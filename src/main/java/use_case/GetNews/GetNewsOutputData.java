package use_case.GetNews;

import entity.CompanyNews;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents the output data for this use case (GetNews).
 * Contains news for the company associated with the specific ticker.
 */
public class GetNewsOutputData {

    /** The ticker associated with the company whose news was retrieved by this use case. */
    String ticker;

    /** A list of maps, each containing details about one news item/article. */
    List<Map<String, String>> newsItems;

    /**
     * Constructs a new GetNewsOutputData instance with the specified ticker symbol
     * and a list of CompanyNews items.
     *
     * @param ticker The ticker associated with the company whose news was retrieved by this use case.
     * @param companyNewsItems A list of CompanyNews items, each containing information for a news article.
     */
    public GetNewsOutputData(String ticker, List<CompanyNews> companyNewsItems) {

        this.ticker = ticker;
        this.newsItems = new ArrayList<>();

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

    /**
     * Gets the ticker associated with the company whose news was retrieved.
     *
     * @return The ticker.
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets the list of news items produced by this use case, where each item corresponds to a news article,
     * each represented as a map with the five following keys: "category," "datetime," "headline," "url," and "summary."
     * These keys map to the relevant information that was retrieved, such as the url of the actual news article.
     *
     * @return The list of news items (as maps).
     */
    public List<Map<String, String>> getNewsItems() {
        return newsItems;
    }

}
