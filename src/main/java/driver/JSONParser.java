package driver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.TransactionHistory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class JSONParser {
    /**
     * Reads the user.json file and grabs the user portfolio
     *
     * @return  a hashmap mapping tickers to amounts of those stocks owned (user portfolio)
     */
    public HashMap<String, Double> getPortfolio() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("user.json")) {
            Type type = new TypeToken<HashMap<String, Double>>(){}.getType();
            HashMap<String, Double> map = gson.fromJson(reader, type);

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Grabs the transaction history of a user. This contains every transaction ever made by the user,
     * including buy, sell and reset transactions.
     *
     * @return  map from ticker to a Transaction history, which contains every transaction ever done with
     *          that specific stock
     */
    public HashMap<String, TransactionHistory> getHistory() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<TransactionHistory>>(){}.getType();
        HashMap<String, TransactionHistory> historyMap = new HashMap<>();

        try (FileReader reader = new FileReader("user.json")) {
            List<TransactionHistory> historyList = gson.fromJson(reader, type);

            for (TransactionHistory history : historyList) {
                historyMap.put(history.getStock().getTicker(), history);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return historyMap;
    }

}
