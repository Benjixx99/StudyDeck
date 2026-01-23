package bx.app.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

internal interface BaseDao<T> {
    fun observeAll(): Flow<List<T>>

    suspend fun getAll(): List<T>

    suspend fun getById(id: Long): T

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)

    suspend fun deleteAll()
}