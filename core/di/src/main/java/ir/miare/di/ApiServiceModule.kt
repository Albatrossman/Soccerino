package ir.miare.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.data.service.http.PlayerApiService
import ir.miare.data.service.http.PlayerApiServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiServiceModule {

    @Binds
    @Singleton
    abstract fun bindPlayerApiService(impl: PlayerApiServiceImpl): PlayerApiService

}