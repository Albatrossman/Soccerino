package ir.miare.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import ir.miare.common.util.DispatcherProvider
import ir.miare.data.util.mock.JsonAssetMockEngine
import ir.miare.data.util.mock.JsonAssetParser
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMockEngine(dispatchers: DispatcherProvider, json: Json, parser: JsonAssetParser): MockEngine {
        return JsonAssetMockEngine(
            dispatchers = dispatchers,
            json = json,
            parser = parser,
            assetFileName = "data.json"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(engine: MockEngine, json: Json): HttpClient {
        return HttpClient(engine = engine) {
            install(plugin = DefaultRequest) {
                this.url(urlString = "https://miare-test.com/")
            }

            install(plugin = ContentNegotiation) {
                this.json(json = json)
            }

            install(plugin = Logging) {
                this.logger = Logger.ANDROID
                this.level = LogLevel.ALL
            }
        }
    }

}