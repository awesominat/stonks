package drivers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.TransactionHistory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class JSONParser {
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

//    public HashMap<String, Double> getPortfolio() {
//        String line;
//        HashMap<String, Double> map = new HashMap<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader("user.csv"))) {
//            while ((line = br.readLine()) != null) {
//                String[] keyValue = line.split(",");
//                String key = keyValue[0].trim();
//                Double value = Double.parseDouble(keyValue[1].trim());
//                map.put(key, value);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return map;
//    }
}