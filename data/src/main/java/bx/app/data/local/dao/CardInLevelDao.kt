package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.local.entity.CardInLevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CardInLevelDao : BaseDao<CardInLevelEntity> {
    @Query("SELECT * FROM card_in_level")
    override fun observeAll(): Flow<List<CardInLevelEntity>>

    @Query("SELECT * FROM card_in_level WHERE level_id = :id LIMIT 1")
    override suspend fun getById(id: Long): CardInLevelEntity

    @Query("SELECT * FROM card_in_level WHERE card_id = :cardId AND level_id = :levelId LIMIT 1")
    suspend fun getByIds(cardId: Long, levelId: Long): CardInLevelEntity

    @Query("DELETE FROM card_in_level")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM card_in_level WHERE card_id = :cardId AND level_id = :levelId")
    fun countById(cardId: Long, levelId: Long): Int
}