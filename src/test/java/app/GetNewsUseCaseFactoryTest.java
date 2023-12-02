package app;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetNews.GetNewsController;
import interface_adapter.GetNews.GetNewsViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.APIAccessInterface;
import view.GetNewsView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetNewsUseCaseFactoryTest {

    @Mock
    private ViewManagerModel viewManagerModel;
    @Mock
    private GetNewsViewModel getNewsViewModel;
    @Mock
    private DashboardViewModel dashboardViewModel;
    @Mock
    private APIAccessInterface apiAccessInterface;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        GetNewsView getNewsView = GetNewsUseCaseFactory.create(
                viewManagerModel,
                getNewsViewModel,
                dashboardViewModel,
                apiAccessInterface
        );

        assertNotNull(getNewsView, "GetNewsView should not be null");
    }
}
