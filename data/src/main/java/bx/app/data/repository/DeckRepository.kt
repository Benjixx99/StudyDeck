package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.DeckEntity
import bx.app.data.model.DeckModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeckRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<DeckEntity>(database.deckDao())
    private val deckDao  = database.deckDao()

    fun getAll(): Flow<List<DeckModel>> = baseRepo.flowList.map { it.filterIsInstance<DeckModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as DeckModel
    suspend fun insert(deck: DeckModel) = baseRepo.insert(deck.toEntity())
    suspend fun deleteById(id: Long) = deckDao.deleteById(id)

    suspend fun upsert(deck: DeckModel): Long {
        if (deck.id <= 0) {
            return deckDao.insertWithDefaultLevel(database, deck.toEntity())
        }
        else {
            baseRepo.update(deck.toEntity())
            return deck.id
        }
    }
}