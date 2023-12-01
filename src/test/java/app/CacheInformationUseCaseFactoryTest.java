//package app;
//
//import interface_adapter.CacheStockInformation.CacheStockInformationController;
//import interface_adapter.CacheStockInformation.CacheStockInformationViewModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import use_case.APIAccessInterface;
//import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class CacheInformationUseCaseFactoryTest {
//
//    @Mock
//    private CacheStockInformationViewModel cacheStockInformationViewModel;
//    @Mock
//    private CacheStockInformationDataAccessInterface fileUserDataAccessObject;
//    @Mock
//    private APIAccessInterface apiAccessInterface;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateCacheStockInformationUseCase() {
//        CacheStockInformationController controller = CacheInformationUseCaseFactory.createCacheStockInformationUseCase(
//                cacheStockInformationViewModel,
//                fileUserDataAccessObject,
//                apiAccessInterface
//        );
//
//        assertNotNull(controller, "CacheStockInformationController should not be null");
//    }
//}