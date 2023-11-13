package use_cases;

import entities.CompanyInformation;
import entities.CompanyNews;
import entities.PricePoint;

import java.time.LocalDate;
import java.util.List;

public interface APIAccessInterface {
    boolean isMarketOpen();
    CompanyInformation getCompanyProfile(String ticker);
    List<CompanyNews> getCompanyNews(String ticker, LocalDate from, LocalDate to);

    PricePoint getCurrentPrice(String ticker); // called Quote in finnhub for example

    // TODO check if we want to create specific methods for timeframes or generalize the resolution
    List<PricePoint> getLastMonthPrices(String ticker); // the from and to can be calculated implicitly
}
