package app;

import interface_adapter.CacheStockInformation.CacheStockInformationController;
import interface_adapter.CacheStockInformation.CacheStockInformationPresenter;
import interface_adapter.CacheStockInformation.CacheStockInformationViewModel;
import use_case.APIAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationInteractor;
import use_case.CacheStockInformation.CacheStockInformationOutputBoundary;

public class CacheInformationUseCaseFactory {
    private CacheInformationUseCaseFactory() {}

    public static CacheStockInformationController createCacheStockInformationUseCase(
            CacheStockInformationViewModel cacheStockInformationViewModel,
            CacheStockInformationDataAccessInterface fileUserDataAccessObject,
            APIAccessInterface apiAccessInterface
    ) {
        CacheStockInformationOutputBoundary presenter = new CacheStockInformationPresenter(
                cacheStockInformationViewModel
        );
        CacheStockInformationInteractor interactor = new CacheStockInformationInteractor(
                presenter,
                fileUserDataAccessObject,
                apiAccessInterface
        );
        return new CacheStockInformationController(
                interactor
        );

    }
}
