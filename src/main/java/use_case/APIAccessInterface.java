package use_case;

import entity.CompanyInformation;
import entity.CompanyNews;
import entity.PricePoint;
import entity.StockInformation;

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
