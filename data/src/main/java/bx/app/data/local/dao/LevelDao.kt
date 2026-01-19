package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.enums.IntervalType
import bx.app.data.local.entity.LevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LevelDao : BaseDao<LevelEntity> {
    @Query("SELECT * FROM level")
    override fun observeAll(): Flow<List<LevelEntity>>

    @Query("SELECT * FROM level WHERE deck_id = :id ORDER BY (interval_number * interval_type)")
    fun observeByDeckId(id: Long): Flow<List<LevelEntity>>

    @Query("SELECT * FROM level WHERE id = :id")
    override suspend fun getById(id: Long): LevelEntity

    @Query("SELECT id FROM level WHERE deck_id = :id ORDER BY (interval_number * interval_type) LIMIT 1")
    suspend fun getFirstByDeckId(id: Long): Long

    @Query("""
        SELECT id FROM level
        WHERE (interval_number * interval_type) > :intervalInDays AND deck_id = :id
        LIMIT 1
    """)
    suspend fun getNextLevelId(intervalInDays: Int, id: Long): Long?

    @Query("""
        SELECT id FROM level
        WHERE (interval_number * interval_type) < :intervalInDays AND deck_id = :id
        ORDER BY (interval_number * interval_type) DESC
        LIMIT 1
    """)
    suspend fun getPriorLevelId(intervalInDays: Int, id: Long): Long?

    @Query("DELETE FROM level")
    override suspend fun deleteAll()

    @Query("DELETE FROM level WHERE deck_id = :id")
    suspend fun deleteByDeckId(id: Long)

    @Query("DELETE FROM level WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("""
        SELECT EXISTS(
            SELECT 1 
            FROM level 
            WHERE interval_number = :intervalNumber 
                AND interval_type = :intervalType 
                AND deck_id = :id
                AND id != :excludeId
        )
    """)
    suspend fun existsIntervalByDeckId(
        id: Long,
        excludeId: Long,
        intervalNumber: Int,
        intervalType: IntervalType
    ): Boolean

    @Query("SELECT count(*) FROM level WHERE id = :id")
    fun countById(id: Long): Int
}