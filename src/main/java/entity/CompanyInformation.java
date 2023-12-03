package entity;

public class CompanyInformation {
    String country;
    String name;
    String ticker;
    String weburl;
    String ipo;

    /**
     * CompanyInformation constructor used in the buy use case
     *
     * @param country   country that the company is based in
     * @param name      name of the company
     * @param ticker    stock ticker of the company
     * @param weburl    url of the company website
     * @param ipo       ipo of the company
     */
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

}
