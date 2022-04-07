package dev.buchstabet.signalbox.codenumber;

import com.google.gson.*;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;

import java.lang.reflect.Type;

public class GsonPositionData<V extends PositionData> implements JsonDeserializer<V>, JsonSerializer<V> {
  @Override
  public V deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Position position = context.deserialize(json.getAsJsonObject().get("position"), Position.class);
    return (V) position.getPositionData().orElse(null);
  }

  @Override
  public JsonElement serialize(V src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("position", context.serialize(src.getPosition()));
    return jsonObject;
  }
}
