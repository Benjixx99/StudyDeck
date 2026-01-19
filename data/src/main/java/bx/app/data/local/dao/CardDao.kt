package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CardDao : BaseDao<CardEntity> {
    @Query("SELECT * FROM card")
    override fun observeAll(): Flow<List<CardEntity>>

    @Query("SELECT * FROM card WHERE deck_id = :id")
    fun observeById(id: Long): Flow<List<CardEntity>>

    @Query("""
        SELECT c.* 
        FROM card AS c
        JOIN card_in_level AS cil ON cil.card_id = c.id
        JOIN level AS l ON l.id = cil.level_id
        WHERE cil.level_id = :id 
        AND (
            cil.last_time_learned_date IS NULL
            OR (strftime('%s','now') - (cil.last_time_learned_date / 1000.0)) 
                / 86400.0 >= l.interval_number * l.interval_type
        )
    """)
    fun observeByLevelId(id: Long): Flow<List<CardEntity>>

    @Query("SELECT * FROM card WHERE id = :id")
    override suspend fun getById(id: Long): CardEntity

    @Query("DELETE FROM card")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM card WHERE id = :id")
    fun countById(id: Long): Int

    @Query("DELETE FROM card WHERE deck_id = :id")
    suspend fun deleteCardsByDeckId(id: Long)

    @Query("DELETE FROM card WHERE id = :id")
    suspend fun deleteCardById(id: Long)
}