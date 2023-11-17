package main.java.entities;

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

    //    {
//        "category": "technology",
//            "datetime": 1596589501,
//            "headline": "Square surges after reporting 64% jump in revenue, more customers using Cash App",
//            "id": 5085164,
//            "image": "https://image.cnbcfm.com/api/v1/image/105569283-1542050972462rts25mct.jpg?v=1542051069",
//            "related": "",
//            "source": "CNBC",
//            "summary": "Shares of Square soared on Tuesday evening after posting better-than-expected quarterly results and strong growth in its consumer payments app.",
//            "url": "https://www.cnbc.com/2020/08/04/square-sq-earnings-q2-2020.html"
//    },
}
