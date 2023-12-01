package interface_adapter.CacheStockInformation;

import java.util.HashMap;
import java.util.List;

public class CacheStockInformationState {
    private HashMap<String, List<Double>> stockInformationMap = null;

    public void setStockInformationMap(HashMap<String, List<Double>> stockInformationMap) {
        this.stockInformationMap = stockInformationMap;
    }

    public HashMap<String, List<Double>> getStockInformationMap() {
        return stockInformationMap;
    }

    public CacheStockInformationState() {

    }
}
