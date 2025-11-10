package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardEntity
import bx.app.data.model.CardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardRepository internal constructor(database: AppDatabase) {
    private val baseRepo = BaseRepository<CardEntity>(database.cardDao())

    fun getAll(): Flow<List<CardModel>> = baseRepo.flowList.map { it.filterIsInstance<CardModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as CardModel
    suspend fun insert(user: CardModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: CardModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: CardModel) = baseRepo.delete(user.toEntity())
}