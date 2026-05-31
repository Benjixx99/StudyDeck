package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import bx.app.data.enums.ConfigScope
import bx.app.data.local.entity.ConfigEntity

@Dao
internal interface ConfigDao {
    @Query("SELECT value FROM config WHERE `key` = :key AND scope = :scope")
    suspend fun getValue(key: String, scope: ConfigScope): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ConfigEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: ConfigEntity)
}