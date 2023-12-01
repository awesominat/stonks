package interface_adapter.CacheStockInformation;

import use_case.CacheStockInformation.CacheStockInformationInputBoundary;

public class CacheStockInformationController {
    final private CacheStockInformationInputBoundary cacheStockInformationInteractor;

    public CacheStockInformationController(CacheStockInformationInputBoundary cacheStockInformationInteractor) {
        this.cacheStockInformationInteractor = cacheStockInformationInteractor;
    }

    public void execute() {
        cacheStockInformationInteractor.execute();
    }
}
