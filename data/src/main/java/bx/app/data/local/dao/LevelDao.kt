package bx.app.data.local.dao

import androidx.room.Query
import bx.app.data.local.entity.LevelEntity
import kotlinx.coroutines.flow.Flow

internal interface LevelDao : BaseDao<LevelEntity> {
    @Query("SELECT * FROM level")
    override fun observeAll(): Flow<List<LevelEntity>>

    @Query("SELECT * FROM level WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): LevelEntity

    @Query("DELETE FROM level")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM level WHERE id = :id")
    fun countById(id: Long): Int
}