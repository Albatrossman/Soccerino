package ir.miare.data.util.mock

import kotlinx.serialization.DeserializationStrategy

interface JsonAssetParser {

    suspend fun <T : Any> parse(fileName: String, deserializer: DeserializationStrategy<T>): T

    suspend fun <T : Any> parse(fileName: String, deserializer: () -> DeserializationStrategy<T>): T

}