package entity;

import java.time.LocalDate;

public class CompanyNews {
    String category;
    LocalDate datetime;
    String headline;
    String url;
    String summary;

    public CompanyNews(String category, LocalDate datetime, String headline, String url, String summary) {
        this.category = category;
        this.datetime = datetime;
        this.headline = headline;
        this.url = url;
        this.summary = summary;
    }

    public String getCategory() {return category;}

    public LocalDate getDatetime() {return datetime;}

    public String getHeadline() {return headline;}

    public String getUrl() {return url;}

    public String getSummary() {return summary;}

    public String toString() {
        return "CompanyNews{\n" +
                "category='" + this.category + "', \n" +
                "headline='" + this.headline + "', \n" +
                "summary='" + this.summary + "', \n" +
                "url='" + this.url + "', \n" +
                "datetime='" + this.datetime.toString() + "'\n" +
                "}";
    }


}
