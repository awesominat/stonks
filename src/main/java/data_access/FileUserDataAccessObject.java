package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import drivers.LocalDateTypeAdapter;
import drivers.TransactionDeserializer;
import entities.CommonUser;
import entities.Transaction;
import entities.User;
import entities.UserFactory;
import use_cases.Buy.BuyDataAccessInterface;
import use_cases.Dashboard.DashboardDataAccessInterface;
import use_cases.GetTransactionHistory.GetTransactionHistoryDataAccessInterface;
import use_cases.ResetBalance.ResetBalanceDataAccessInterface;
import use_cases.Sell.SellDataAccessInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class FileUserDataAccessObject implements
        BuyDataAccessInterface,
        SellDataAccessInterface,
        ResetBalanceDataAccessInterface,
        DashboardDataAccessInterface,
        GetTransactionHistoryDataAccessInterface
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
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
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
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        String json = gson.toJson(user);

        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User get() {
        return user;
    }
}
