package pl.humberd.headlesskclientmobile

import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.Instant


object InstantTypeConverter : JsonSerializer<Instant>, JsonDeserializer<Instant> {
    override fun serialize(src: Instant, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toEpochMilli())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Instant {
        return Instant.ofEpochMilli(json.asLong)
    }
}
