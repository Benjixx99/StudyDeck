package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.DeckEntity
import bx.app.data.model.DeckModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeckRepository internal constructor(database: AppDatabase) {
    private val baseRepo = BaseRepository<DeckEntity>(database.deckDao())

    fun getAll(): Flow<List<DeckModel>> = baseRepo.flowList.map { it.filterIsInstance<DeckModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as DeckModel
    suspend fun insert(user: DeckModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: DeckModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: DeckModel) = baseRepo.delete(user.toEntity())
}