package use_case.CacheStockInformation;

import entity.User;

public interface CacheStockInformationDataAccessInterface {
    void save();
    User get();
}
