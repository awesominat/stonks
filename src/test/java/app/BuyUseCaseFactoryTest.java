//package app;
//
//import interface_adapter.Buy.BuyViewModel;
//import interface_adapter.Dashboard.DashboardViewModel;
//import interface_adapter.ViewManagerModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import use_case.APIAccessInterface;
//import use_case.Buy.BuyDataAccessInterface;
//import view.BuyView;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class BuyUseCaseFactoryTest {
//
//    @Mock
//    private ViewManagerModel viewManagerModel;
//    @Mock
//    private BuyViewModel buyViewModel;
//    @Mock
//    private DashboardViewModel dashboardViewModel;
//    @Mock
//    private BuyDataAccessInterface buyDataAccessInterface;
//    @Mock
//    private APIAccessInterface apiAccessInterface;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testBuyCreate() {
//        BuyView buyView = BuyUseCaseFactory.create(
//                viewManagerModel,
//                buyViewModel,
//                dashboardViewModel,
//                buyDataAccessInterface,
//                apiAccessInterface
//        );
//
//        assertNotNull(buyView, "BuyView should not be null");
//    }
//}