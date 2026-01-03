package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import bx.app.data.enums.IntervalType
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.DeckEntity
import bx.app.data.local.entity.LevelEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DeckDao : BaseDao<DeckEntity> {
    @Query("SELECT * FROM deck")
    override fun observeAll(): Flow<List<DeckEntity>>

    @Query("SELECT * FROM deck WHERE id = :id")
    override suspend fun getById(id: Long): DeckEntity

    @Query("DELETE FROM deck")
    override suspend fun deleteAll()

    @Query("DELETE FROM deck WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT count(*) FROM deck WHERE id = :id")
    fun countById(id: Long): Int

    @Transaction
    suspend fun insertWithDefaultLevel(database: AppDatabase, deck: DeckEntity): Long {
        val id = insert(deck)
        if (id > 0) {
            database.levelDao().insert(
                LevelEntity(
                    name = "Once a day",
                    intervalNumber = 7,
                    intervalType = IntervalType.WEEK,
                    deckId = id
                )
            )
        }
        return id
    }
}