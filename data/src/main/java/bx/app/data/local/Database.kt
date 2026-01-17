package bx.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bx.app.data.converter.IntervalTypeConverter
import bx.app.data.converter.LocalDateTimeConverter
import bx.app.data.local.dao.AudioSideDao
import bx.app.data.local.dao.CardDao
import bx.app.data.local.dao.CardInLevelDao
import bx.app.data.local.dao.CardWithSidesDao
import bx.app.data.local.dao.DeckDao
import bx.app.data.local.dao.LevelDao
import bx.app.data.local.dao.TextSideDao
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.local.entity.CardEntity
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.local.entity.DeckEntity
import bx.app.data.local.entity.LevelEntity
import bx.app.data.local.entity.TextSideEntity

@Database(
    version = DatabaseBuilder.LATEST_VERSION,
    entities = [
        DeckEntity::class,
        CardEntity::class,
        LevelEntity::class,
        TextSideEntity::class,
        AudioSideEntity::class,
        CardInLevelEntity::class,
    ],
)
@TypeConverters(
    IntervalTypeConverter::class,
    LocalDateTimeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun deckDao(): DeckDao
    internal abstract fun cardDao(): CardDao
    internal abstract fun levelDao(): LevelDao
    internal abstract fun textSideDao(): TextSideDao
    internal abstract fun audioSideDao(): AudioSideDao
    internal abstract fun cardInLevelDao(): CardInLevelDao
    internal abstract fun cardWithSidesDao(): CardWithSidesDao
}

object DatabaseBuilder {
    private var instance: AppDatabase? = null
    const val DATABASE_NAME = "dev-database"
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