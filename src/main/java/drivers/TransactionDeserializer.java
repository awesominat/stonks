package drivers;

import com.google.gson.*;
import entities.BuyTransaction;
import entities.SellTransaction;
import entities.TopupTransaction;
import entities.Transaction;

import java.lang.reflect.Type;

public class TransactionDeserializer implements JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElem = jsonObject.get("type");
        if (typeElem == null) {
            throw new JsonParseException("unable to find transaction type");
        }

        String type = typeElem.getAsString();
        Class<? extends Transaction> transactionClass = getTransactionClass(type);
        return context.deserialize(jsonObject, transactionClass);
    }

    private Class<? extends Transaction> getTransactionClass(String type) {
        return switch (type) {
            case "BUY" -> BuyTransaction.class;
            case "SELL" -> SellTransaction.class;
            case "TOPUP" -> TopupTransaction.class;
            default -> throw new IllegalArgumentException("unknown transaction type " + type);
        };
    }
}
