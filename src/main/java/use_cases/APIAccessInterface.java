package use_cases;

import entities.CompanyInformation;
import entities.CompanyNews;
import entities.PricePoint;
import entities.StockInformation;

import java.time.LocalDate;
import java.util.List;

public interface APIAccessInterface {
    String getAppName();
    boolean isMarketOpen();
    CompanyInformation getCompanyProfile(String ticker);
    List<CompanyNews> getCompanyNews(String ticker, LocalDate from, LocalDate to);

    PricePoint getCurrentPrice(String ticker); // called Quote in finnhub for example
    StockInformation getCurrentStockInformation(String ticker);

}
