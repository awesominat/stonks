package data_access;


import entity.User;
import entity.UserFactory;
import use_case.Buy.BuyDataAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
import use_case.Dashboard.DashboardDataAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_case.ResetBalance.ResetBalanceDataAccessInterface;
import use_case.Sell.SellDataAccessInterface;

public class InMemoryUserDataAccessObject implements
        BuyDataAccessInterface,
        SellDataAccessInterface,
        ResetBalanceDataAccessInterface,
        DashboardDataAccessInterface,
        GetTransactionHistoryDataAccessInterface,
        CacheStockInformationDataAccessInterface {

    private final User user;
    @Override
    public void save() {
    }

    public InMemoryUserDataAccessObject(UserFactory userFactory) {
        user = userFactory.create();
        save();
    }

    @Override
    public User get() {
        return this.user;
    }
}
