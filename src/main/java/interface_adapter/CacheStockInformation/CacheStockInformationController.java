package interface_adapter.CacheStockInformation;

import use_case.CacheStockInformation.CacheStockInformationInputBoundary;

public class CacheStockInformationController {
    final private CacheStockInformationInputBoundary cacheStockInformationInteractor;

    /**
     * Controller for the cache stock information use case.
     *
     * @param cacheStockInformationInteractor   Interactor for the cache stock information use case
     */
    public CacheStockInformationController(CacheStockInformationInputBoundary cacheStockInformationInteractor) {
        this.cacheStockInformationInteractor = cacheStockInformationInteractor;
    }

    /**
     * Execute call which causes the interactor to execute. This is the method that is
     * called every ~15 seconds asynchronously in Main.java
     */
    public void execute() {
        cacheStockInformationInteractor.execute();
    }
}
