package driver;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Serializes a datetime object before placing it in the user.json file (stored with transaction history)
     *
     * @param dateTime      LocalDateTime object to be serialized
     * @param typeOfSrc     Type of source object that is being serialized
     * @param context       an interface that is used by the json element returned. Not relevant for our
     *                      usage of this function, since this is used by the library
     * @return              Returns a serialized element of a json object
     */
    @Override
    public JsonElement serialize(final LocalDateTime dateTime, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        return new JsonPrimitive(dateTime.format(formatter));
    }

    /**
     * Deserializes a json element to a LocalDateTime object
     *
     * @param json      Json element to be deserialize
     * @param typeOfT   type of returned object
     * @param context       an interface that is used by the json element returned. Not relevant for our
     *                      usage of this function, since this is used by the library
     * @return          Deserialized date time object
     * @throws JsonParseException   throws this error if unable to parse json element
     */
    @Override
    public LocalDateTime deserialize(final JsonElement json, final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
