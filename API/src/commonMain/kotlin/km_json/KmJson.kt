package km_json

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.serializer

object KmJson {
    inline fun <reified V, S> toJson(data: V, serializer: KSerializer<S>): String {
        return if (data is List<*>) {
            val value = data as List<S>
            val builder = StringBuilder()
            builder.append("[")
            value.forEach {
                if (builder.toString() != "[") {
                    builder.append(",")
                }
                builder.append(Json.encodeToString(serializer, it))
            }
            builder.append("]")
            builder.toString()
        } else {
            Json.encodeToString(serializer, data as S)
        }
    }

    @OptIn(InternalSerializationApi::class)
    inline fun <reified T> fromJsonList(data: String, serializable: KSerializer<T>): List<T> {
        return if (data.isBlank()) {
            emptyList()
        } else {

            val jsonArray = Json.decodeFromString(JsonArray::class.serializer(), data)

            // Convert each element in the JSON array to a MyModel object
            return jsonArray.map { jsonElement ->
                Json.decodeFromString(serializable, jsonElement.toString())
            }

        }
    }

    inline fun <reified T> fromJson(data: String, serializable: KSerializer<T>): T {
        return Json.decodeFromString(serializable, data)
    }
}