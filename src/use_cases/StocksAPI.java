package use_cases;

import entities.PricePoint;
import entities.Stock;

import java.util.ArrayList;

public class StocksAPI {

    public String getStockInfo(String stockTicker) {
        System.out.println("get stock info");
        return "temp";
    }

    public Stock parseRawInfo(String[] info) {
        return new Stock(0.0, new ArrayList<PricePoint>(), "f", "fa");
    }

    public static void main(String[] args) {
        // TODO
        // add API communication
    }
}
