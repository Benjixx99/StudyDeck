package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.local.entity.LevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LevelDao : BaseDao<LevelEntity> {
    @Query("SELECT * FROM level")
    override fun observeAll(): Flow<List<LevelEntity>>

    @Query("SELECT * FROM level WHERE deck_id = :id ORDER BY interval_type, interval_number DESC")
    fun observeByDeckId(id: Long): Flow<List<LevelEntity>>

    @Query("SELECT * FROM level WHERE id = :id")
    override suspend fun getById(id: Long): LevelEntity

    @Query("SELECT id FROM level WHERE deck_id = :id ORDER BY interval_type, interval_number DESC LIMIT 1")
    suspend fun getFirstByDeckId(id: Long): Long

    @Query("DELETE FROM level")
    override suspend fun deleteAll()

    @Query("DELETE FROM level WHERE deck_id = :id")
    suspend fun deleteByDeckId(id: Long)

    @Query("DELETE FROM level WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT count(*) FROM level WHERE id = :id")
    fun countById(id: Long): Int
}