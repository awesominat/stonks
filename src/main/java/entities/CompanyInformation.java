package entities;

public class CompanyInformation {
    String country;
    String name;
    String ticker;
    String weburl;
    String ipo;

    public CompanyInformation(String country, String name, String ticker, String weburl, String ipo) {
        this.country = country;
        this.name = name;
        this.ticker = ticker;
        this.weburl = weburl;
        this.ipo = ipo;
    }

    public String getCountry() {return country;}

    public String getName() {return name;}

    public String getTicker() {return ticker;}

    public String getWeburl() {return weburl;}

    public String getIpo() {return ipo;}

    public String toString() {
        return "CompanyInformation{\n" +
                "country='" + this.country + "', \n" +
                "name='" + this.name + "', \n" +
                "ticker='" + this.ticker + "', \n" +
                "weburl='" + this.weburl + "', \n" +
                "ipo='" + this.ipo + "'\n" +
                "}";
    }

    //      "country": "US",
//              "currency": "USD",
//              "exchange": "NASDAQ/NMS (GLOBAL MARKET)",
//              "ipo": "1980-12-12",
//              "marketCapitalization": 1415993,
//              "name": "Apple Inc",
//              "phone": "14089961010",
//              "shareOutstanding": 4375.47998046875,
//              "ticker": "AAPL",
//              "weburl": "https://www.apple.com/",
//              "logo": "https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png",
//              "finnhubIndustry":"Technology"
}
