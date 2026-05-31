package bx.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import bx.app.data.converter.IntervalTypeConverter
import bx.app.data.converter.LocalDateTimeConverter
import bx.app.data.local.dao.AudioSideDao
import bx.app.data.local.dao.CardDao
import bx.app.data.local.dao.CardInLevelDao
import bx.app.data.local.dao.CardWithSidesDao
import bx.app.data.local.dao.ConfigDao
import bx.app.data.local.dao.DeckDao
import bx.app.data.local.dao.LevelDao
import bx.app.data.local.dao.TextSideDao
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.local.entity.CardEntity
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.local.entity.ConfigEntity
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
        ConfigEntity::class
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
    internal abstract fun configDao(): ConfigDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE audio_side
            RENAME COLUMN file_mame TO file_name
        """.trimIndent())
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
      CREATE TABLE IF NOT EXISTS `config` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `key` TEXT NOT NULL,
        `scope` TEXT NOT NULL,
        `value` TEXT NOT NULL
      )
    """.trimIndent())
        database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_config_key_scope` ON `config` (`key`, `scope`)")
    }
}

object DatabaseBuilder {
    private var instance: AppDatabase? = null
    const val DATABASE_NAME = "dev-database"
    const val LATEST_VERSION = 3

    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    //.fallbackToDestructiveMigration(true)
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }
        }
        return instance!!
    }
}