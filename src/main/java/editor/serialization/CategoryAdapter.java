package editor.serialization;

import java.awt.Color;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import editor.collection.deck.Category;
import editor.database.card.Card;
import editor.filter.Filter;

/**
 * A JSON serializer and deserializer for {@link Category}.
 * 
 * @author Alec Roelke
 */
public class CategoryAdapter implements JsonSerializer<Category>, JsonDeserializer<Category>
{
    @Override
    public JsonElement serialize(Category src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject category = new JsonObject();
        category.addProperty("name", src.getName());
        category.add("filter", context.serialize(src.getFilter()));
        category.add("whitelist", context.serialize(src.getWhitelist()));
        category.add("blacklist", context.serialize(src.getBlacklist()));
        category.add("color", context.serialize(src.getColor()));

        return category;
    }

    @Override
    public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject obj = json.getAsJsonObject();

        Category category = new Category();
        category.setName(obj.get("name").getAsString());
        category.setFilter(context.deserialize(obj.get("filter"), Filter.class));
        for (JsonElement element : obj.get("whitelist").getAsJsonArray())
            category.include(context.deserialize(element, Card.class));
        for (JsonElement element : obj.get("blacklist").getAsJsonArray())
            category.exclude(context.deserialize(element, Card.class));
        category.setColor(context.deserialize(obj.get("color"), Color.class));

        return category;
    }
}