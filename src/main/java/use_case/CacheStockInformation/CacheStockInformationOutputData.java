package use_case.CacheStockInformation;

import java.util.HashMap;
import java.util.List;

public class CacheStockInformationOutputData {
    private HashMap<String, List<Double>> stockInformationMap;

    public CacheStockInformationOutputData(HashMap<String, List<Double>> stockInformationMap) {
        this.stockInformationMap = stockInformationMap;
    }

    public HashMap<String, List<Double>> getStockInformationMap() {
        return stockInformationMap;
    }
}
