package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardEntity
import bx.app.data.model.CardModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class CardRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<CardEntity>(database.cardDao())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeById(idFlow: Flow<Long>): Flow<List<CardModel>> =
        idFlow.flatMapLatest { id ->
            database.cardDao().observeById(id).map { list -> list.map { it.toModel() } }
        }

    suspend fun getById(id: Long) = baseRepo.getById(id) as CardModel
    suspend fun insert(card: CardModel) = baseRepo.insert(card.toEntity())
    suspend fun update(card: CardModel) = baseRepo.update(card.toEntity())
    suspend fun delete(card: CardModel) = baseRepo.delete(card.toEntity())
}