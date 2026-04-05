package bx.app.data.repository

import androidx.room.RoomRawQuery
import bx.app.data.enums.SortMode
import bx.app.data.local.AppDatabase
import bx.app.data.local.CardQuery
import bx.app.data.local.entity.CardEntity
import bx.app.data.model.CardModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class CardRepository(database: AppDatabase) {
    private val cardDao = database.cardDao()
    private val baseRepo = BaseRepository<CardEntity>(cardDao)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeById(idFlow: Flow<Long>, sortModeFlow: Flow<SortMode>): Flow<List<CardModel>> =
        combine(idFlow, sortModeFlow) { id, sortMode -> id to sortMode }
            .flatMapLatest { (id, sortMode) ->
                val orderByClause = when (sortMode) {
                    SortMode.ID_ASC -> "ORDER BY c.id ASC"
                    SortMode.ID_DESC -> "ORDER BY c.id DESC"
                    SortMode.TEXT_ASC -> "ORDER BY front_text ASC"
                    SortMode.TEXT_DESC -> "ORDER BY front_text DESC"
                    SortMode.LENGTH_ASC -> "ORDER BY front_text_length ASC"
                    SortMode.LENGTH_DESC -> "ORDER BY front_text_length DESC"
                }

                val sql = """
                    ${CardQuery.CARD_WITH_SIDES_CONTENT}
                    WHERE c.deck_id = ?
                    $orderByClause
                """.trimIndent()

                val query = RoomRawQuery(
                    sql = sql,
                    onBindStatement = { it.bindLong(1, id) }
                )
                cardDao.observeById(query).map { list -> list.map { it.toModel() } }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeByLevelId(idFlow: Flow<Long>): Flow<List<CardModel>> =
        idFlow.flatMapLatest { id ->
            cardDao.observeByLevelId(id).map { list -> list.map { it.toModel() } }
        }

    fun countCardsByDeckId(id: Long) = cardDao.countCardsByDeckId(id)

    suspend fun getById(id: Long) = cardDao.getCardWithSideContentById(id).toModel()
    suspend fun insert(card: CardModel) = baseRepo.insert(card.toEntity())
    suspend fun update(card: CardModel) = baseRepo.update(card.toEntity())
    suspend fun delete(card: CardModel) = baseRepo.delete(card.toEntity())
}