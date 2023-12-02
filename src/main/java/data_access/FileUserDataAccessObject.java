package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import driver.LocalDateTimeTypeAdapter;
import driver.TransactionDeserializer;
import entity.CommonUser;
import entity.Transaction;
import entity.User;
import entity.UserFactory;
import use_case.Buy.BuyDataAccessInterface;
import use_case.CacheStockInformation.CacheStockInformationDataAccessInterface;
import use_case.Dashboard.DashboardDataAccessInterface;
import use_case.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_case.ResetBalance.ResetBalanceDataAccessInterface;
import use_case.Sell.SellDataAccessInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileUserDataAccessObject implements
        BuyDataAccessInterface,
        SellDataAccessInterface,
        ResetBalanceDataAccessInterface,
        DashboardDataAccessInterface,
        GetTransactionHistoryDataAccessInterface,
        CacheStockInformationDataAccessInterface
{
    private File jsonFile;
    private User user;

    public FileUserDataAccessObject(String jsonPath, UserFactory userFactory) throws IOException {
        jsonFile = new File(jsonPath);
        if (jsonFile.length() == 0) {
            user = new CommonUser();
            save();
        } else {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Transaction.class, new TransactionDeserializer())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();
            try (FileReader reader = new FileReader(jsonFile)) {
                 this.user = gson.fromJson(reader, CommonUser.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        String json = gson.toJson(user);

        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
            System.out.println("Stored");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User get() {
        return user;
    }
}
