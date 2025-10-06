package ir.miare.data.util.mock

import android.content.res.AssetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

class JsonAssetParserImpl(
    private val assets: AssetManager,
    private val json: Json
) : JsonAssetParser {

    override suspend fun <T : Any> parse(
        fileName: String,
        deserializer: DeserializationStrategy<T>
    ): T {
        return withContext(context = Dispatchers.IO) {
            val inputStream = assets.open(fileName)

            InputStreamReader(inputStream).use { reader ->
                val text = reader.readText()
                json.decodeFromString(deserializer, text)
            }
        }
    }

    override suspend fun <T : Any> parse(
        fileName: String,
        deserializer: () -> DeserializationStrategy<T>
    ): T {
        return parse(fileName = fileName, deserializer = deserializer())
    }

}