package bx.app.data.repository

import androidx.room.RoomRawQuery
import bx.app.core.hasInvalidId
import bx.app.data.enums.SortMode
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.DeckEntity
import bx.app.data.model.DeckModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class DeckRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<DeckEntity>(database.deckDao())
    private val deckDao  = database.deckDao()

    suspend fun getById(id: Long) = baseRepo.getById(id) as DeckModel
    suspend fun deleteById(id: Long) = deckDao.deleteById(id)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeAll(sortModeFlow: Flow<SortMode>): Flow<List<DeckModel>> =
        sortModeFlow.flatMapLatest { sortMode ->
                val orderByClause = when (sortMode) {
                    SortMode.ID_ASC -> "ORDER BY id ASC"
                    SortMode.ID_DESC -> "ORDER BY id DESC"
                    SortMode.TEXT_ASC -> "ORDER BY name ASC"
                    SortMode.TEXT_DESC -> "ORDER BY name DESC"
                    SortMode.LENGTH_ASC -> "ORDER BY name_length ASC"
                    SortMode.LENGTH_DESC -> "ORDER BY name_length DESC"
                }

                val sql = """
                    SELECT *, length(name) AS name_length FROM deck
                    $orderByClause
                """.trimIndent()

                val query = RoomRawQuery(sql = sql)
                deckDao.observeAll(query).map { list -> list.map { it.toModel() } }
            }

    suspend fun upsert(deck: DeckModel): Long {
        if (deck.id.hasInvalidId()) {
            return deckDao.insertWithDefaultLevel(database, deck.toEntity())
        }
        else {
            baseRepo.update(deck.toEntity())
            return deck.id
        }
    }
}