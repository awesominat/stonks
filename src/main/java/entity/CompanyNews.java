package entity;

import java.time.LocalDateTime;

public class CompanyNews {
    String category;
    LocalDateTime datetime;
    String headline;
    String url;
    String summary;

    /**
     * Constructor for CompanyNews
     *
     * @param category      category of the news article
     * @param datetime      timestamp that the article was published
     * @param headline      headline of the article
     * @param url           url of the article
     * @param summary       summary of the article
     */
    public CompanyNews(String category, LocalDateTime datetime, String headline, String url, String summary) {
        this.category = category;
        this.datetime = datetime;
        this.headline = headline;
        this.url = url;
        this.summary = summary;
    }

    public String getCategory() {return category;}

    public LocalDateTime getDatetime() {return datetime;}

    public String getHeadline() {return headline;}

    public String getUrl() {return url;}

    public String getSummary() {return summary;}

}
