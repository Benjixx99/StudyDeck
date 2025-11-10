package bx.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bx.app.data.local.dao.CardDao
import bx.app.data.local.dao.DeckDao
import bx.app.data.local.dao.LevelDao
import bx.app.data.local.entity.CardEntity
import bx.app.data.local.entity.DeckEntity
import bx.app.data.local.entity.LevelEntity

@Database(
    version = DatabaseBuilder.LATEST_VERSION,
    entities = [
        DeckEntity::class,
        CardEntity::class,
        LevelEntity::class,
    ],
)

abstract class AppDatabase : RoomDatabase() {
    internal abstract fun deckDao(): DeckDao
    internal abstract fun cardDao(): CardDao
    internal abstract fun levelDao(): LevelDao
}

object DatabaseBuilder {
    private var instance: AppDatabase? = null
    const val DATABASE_NAME = "test-database"
    const val LATEST_VERSION = 1

    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    //.fallbackToDestructiveMigration(true)
                    .build()
            }
        }
        return instance!!
    }
}