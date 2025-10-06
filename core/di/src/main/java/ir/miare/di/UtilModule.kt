package ir.miare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.miare.common.util.DispatcherProvider
import ir.miare.data.repo.PlayerRepositoryImpl
import ir.miare.data.repo.source.PlayerRemoteDatasource
import ir.miare.data.util.DefaultDispatcherProvider
import ir.miare.data.util.mock.JsonAssetParser
import ir.miare.data.util.mock.JsonAssetParserImpl
import ir.miare.domain.repo.PlayerRepository
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            this.prettyPrint = true
            this.ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provideJsonAssetParser(@ApplicationContext context: Context, json: Json): JsonAssetParser {
        return JsonAssetParserImpl(assets = context.assets, json = json)
    }

}