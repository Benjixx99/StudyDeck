package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.DeckEntity
import bx.app.data.model.DeckModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeckRepository(database: AppDatabase) {
    private val baseRepo = BaseRepository<DeckEntity>(database.deckDao())

    fun getAll(): Flow<List<DeckModel>> = baseRepo.flowList.map { it.filterIsInstance<DeckModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as DeckModel
    suspend fun insert(deck: DeckModel) = baseRepo.insert(deck.toEntity())
    suspend fun update(deck: DeckModel) = baseRepo.update(deck.toEntity())
    suspend fun delete(deck: DeckModel) = baseRepo.delete(deck.toEntity())

    suspend fun upsert(deck: DeckModel): Long {
        if (deck.id <= 0)
            return insert(deck)
        else {
            update(deck)
            return deck.id
        }
    }
}