package dev.timatifey.newsapp.data.database.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.timatifey.newsapp.data.ArticlesRepository
import dev.timatifey.newsapp.data.ArticlesRepositoryImpl
import dev.timatifey.newsapp.data.database.NewsAppDb
import javax.inject.Singleton

/**
 * @author timatifey
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindRepo(imlp: ArticlesRepositoryImpl): ArticlesRepository

    companion object {
        @Provides
        @Singleton
        fun provideDb(
            @ApplicationContext context: Context,
        ): NewsAppDb {
            val db = databaseBuilder(
                context,
                NewsAppDb::class.java, "news-app-db"
            ).build()
            return db
        }
    }
}
