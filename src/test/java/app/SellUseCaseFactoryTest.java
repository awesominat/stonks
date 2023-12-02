package app;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Sell.SellViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.APIAccessInterface;
import use_case.Sell.SellDataAccessInterface;
import view.SellView;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SellUseCaseFactoryTest {

    @Mock
    private ViewManagerModel viewManagerModel;
    @Mock
    private SellViewModel sellViewModel;
    @Mock
    private DashboardViewModel dashboardViewModel;
    @Mock
    private SellDataAccessInterface sellDataAccessInterface;
    @Mock
    private APIAccessInterface apiAccessInterface;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        SellView sellView = SellUseCaseFactory.create(
                viewManagerModel, sellViewModel, dashboardViewModel,
                sellDataAccessInterface, apiAccessInterface
        );

        assertNotNull(sellView, "SellView should not be null");
    }
}
