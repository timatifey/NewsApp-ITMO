package dev.timatifey.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.timatifey.newsapp.data.database.model.ArticleEntity
import dev.timatifey.newsapp.data.database.model.ArticleEntityFts

/**
 * @author timatifey
 */
@Database(entities = [ArticleEntity::class, ArticleEntityFts::class], version = 1)
abstract class NewsAppDb : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}