package entity;

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

}