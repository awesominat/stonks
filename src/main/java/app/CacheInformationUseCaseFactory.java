package app;

import entity.CacheStockInformation;
import interface_adapter.CacheStockInformation.CacheStockInformationController;
import use_case.APIAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationInteractor;

public class CacheInformationUseCaseFactory {
    private CacheInformationUseCaseFactory() {}

    public static CacheStockInformationController createCacheStockInformationUseCase(
            CacheStockInformationDataAccessInterface fileUserDataAccessObject,
            CacheStockInformation cacheStockInformation,
            APIAccessInterface apiAccessInterface
    ) {
        CacheStockInformationInteractor interactor = new CacheStockInformationInteractor(
                fileUserDataAccessObject,
                cacheStockInformation,
                apiAccessInterface
        );
        return new CacheStockInformationController(
                interactor
        );
    }
}
