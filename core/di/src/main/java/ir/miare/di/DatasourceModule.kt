package ir.miare.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.data.repo.source.PlayerLocalDatasource
import ir.miare.data.repo.source.PlayerLocalDatasourceImpl
import ir.miare.data.repo.source.PlayerRemoteDatasource
import ir.miare.data.repo.source.PlayerRemoteDatasourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    abstract fun bindPlayerLocalDatasource(impl: PlayerLocalDatasourceImpl): PlayerLocalDatasource

    @Binds
    @Singleton
    abstract fun bindPlayerRemoteDatasource(impl: PlayerRemoteDatasourceImpl): PlayerRemoteDatasource

}