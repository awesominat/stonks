package app;

import entity.CacheStockInformation;
import entity.CommonUser;
import interface_adapter.Buy.BuyViewModel;
import interface_adapter.Dashboard.DashboardState;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ResetBalance.ResetBalanceController;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import use_case.APIAccessInterface;
import use_case.Dashboard.DashboardDataAccessInterface;
import view.DashboardView;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DashboardUseCaseFactoryTest {

    @Mock
    private ViewManagerModel viewManagerModel;
    @Mock
    private BuyViewModel buyViewModel;
    @Mock
    private SellViewModel sellViewModel;
    @Mock
    private GetNewsViewModel getNewsViewModel;
    @Mock
    private ResetBalanceController resetBalanceController;
    @Mock
    private GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    @Mock
    private APIAccessInterface apiAccessInterface;
    @Mock
    private CacheStockInformation cacheStockInformation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {

        DashboardDataAccessInterface dashboardDataAccessInterface = Mockito.mock(DashboardDataAccessInterface.class);

        Mockito.when(dashboardDataAccessInterface.get()).thenReturn( new CommonUser());

        DashboardViewModel dashboardViewModel = Mockito.mock(DashboardViewModel.class);
        Mockito.when(dashboardViewModel.getState()).thenReturn(new DashboardState());

        DashboardView result = DashboardUseCaseFactory.create(
                viewManagerModel,
                dashboardViewModel,
                buyViewModel,
                sellViewModel,
                getNewsViewModel,
                resetBalanceController,
                getTransactionHistoryViewModel,
                dashboardDataAccessInterface,
                cacheStockInformation,
                apiAccessInterface
        );

        assertNotNull(result, "DashboardView should not be null");
    }
}
