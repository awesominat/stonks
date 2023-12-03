package driver;

import com.google.gson.*;
import entity.BuyTransaction;
import entity.SellTransaction;
import entity.TopupTransaction;
import entity.Transaction;

import java.lang.reflect.Type;

public class TransactionDeserializer implements JsonDeserializer<Transaction> {
    /**
     * Deserializes transactions stored in the user.json file and returns a
     * Transaction object
     *
     * @param json      json element to deserialize
     * @param typeOfT   return type
     * @param context   ??? TODO
     * @return          deserialized json element (Transaction object)
     * @throws JsonParseException   error thrown if unable to parse json element
     */
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

    /**
     * Returns the type of the transaction given a string
     *
     * @param type  is either "BUY", "SELL" or "TOPUP"
     * @return      returns the respective Transaction type class
     */
    private Class<? extends Transaction> getTransactionClass(String type) {
        return switch (type) {
            case "BUY" -> BuyTransaction.class;
            case "SELL" -> SellTransaction.class;
            case "TOPUP" -> TopupTransaction.class;
            default -> throw new IllegalArgumentException("unknown transaction type " + type);
        };
    }
}
