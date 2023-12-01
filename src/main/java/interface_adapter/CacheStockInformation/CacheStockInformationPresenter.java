package interface_adapter.CacheStockInformation;

import use_case.CacheStockInformation.CacheStockInformationOutputBoundary;
import use_case.CacheStockInformation.CacheStockInformationOutputData;

public class CacheStockInformationPresenter implements CacheStockInformationOutputBoundary {
    private final CacheStockInformationViewModel cacheStockInformationViewModel;

    public CacheStockInformationPresenter(
            CacheStockInformationViewModel cacheStockInformationViewModel
    ) {
        this.cacheStockInformationViewModel = cacheStockInformationViewModel;
    }

    @Override
    public void updateCacheSuccess(CacheStockInformationOutputData cacheStockInformationOutputData) {
        CacheStockInformationState state = cacheStockInformationViewModel.getState();
        state.setStockInformationMap(cacheStockInformationOutputData.getStockInformationMap());
        cacheStockInformationViewModel.setState(state);
        cacheStockInformationViewModel.firePropertyChanged();
    }
}
