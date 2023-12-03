package entity;

import java.time.LocalDateTime;

public class CompanyNews {
    String category;
    LocalDateTime datetime;
    String headline;
    String url;
    String summary;

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
