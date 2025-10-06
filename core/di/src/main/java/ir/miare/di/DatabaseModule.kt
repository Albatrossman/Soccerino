package ir.miare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.miare.data.service.room.AppDatabase
import ir.miare.data.service.room.dao.FollowedPlayerDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.build(context = context)
    }

    @Provides
    @Singleton
    fun providePlayerDao(database: AppDatabase): FollowedPlayerDao {
        return database.followedPlayerDao
    }

}