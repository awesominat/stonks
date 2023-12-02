package app;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.GetTransactionHistory.GetTransactionHistoryViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.APIAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import view.TransactionHistoryView;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetTransactionHistoryUseCaseFactoryTest {

    @Mock
    private ViewManagerModel viewManagerModel;
    @Mock
    private GetTransactionHistoryViewModel getTransactionHistoryViewModel;
    @Mock
    private DashboardViewModel dashboardViewModel;
    @Mock
    private GetTransactionHistoryDataAccessInterface getTransactionHistoryDataAccessInterface;
    @Mock
    private APIAccessInterface apiAccessInterface;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        TransactionHistoryView transactionHistoryView = GetTransactionHistoryUseCaseFactory.create(
                viewManagerModel,
                getTransactionHistoryViewModel,
                dashboardViewModel,
                getTransactionHistoryDataAccessInterface,
                apiAccessInterface
        );

        assertNotNull(transactionHistoryView, "TransactionHistoryView should not be null");
    }
}
