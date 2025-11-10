package bx.app.data.local.dao

import androidx.room.Query
import bx.app.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

internal interface CardDao : BaseDao<CardEntity> {
    @Query("SELECT * FROM card")
    override fun observeAll(): Flow<List<CardEntity>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): CardEntity

    @Query("DELETE FROM card")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM card WHERE id = :id")
    fun countById(id: Long): Int
}