package use_case;

import entity.CompanyInformation;
import entity.CompanyNews;
import entity.PricePoint;
import entity.StockInformation;

import java.time.LocalDate;
import java.util.List;

public interface APIAccessInterface {
    public static class TickerNotFoundException extends Exception {
        public TickerNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }
    String getAppName();
    boolean isMarketOpen();
    CompanyInformation getCompanyProfile(String ticker) throws TickerNotFoundException;
    List<CompanyNews> getCompanyNews(String ticker, LocalDate from, LocalDate to) throws TickerNotFoundException;

    PricePoint getCurrentPrice(String ticker) throws TickerNotFoundException; // called Quote in finnhub for example
    StockInformation getCurrentStockInformation(String ticker) throws TickerNotFoundException;

}
