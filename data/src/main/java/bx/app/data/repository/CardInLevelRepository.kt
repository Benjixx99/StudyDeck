package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.model.CardInLevelModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardInLevelRepository(database: AppDatabase) {
    private val dao = database.cardInLevelDao()
    private val baseRepo = BaseRepository<CardInLevelEntity>(dao)

    fun getAll(): Flow<List<CardInLevelModel>> = baseRepo.flowList.map { it.filterIsInstance<CardInLevelModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as CardInLevelModel
    suspend fun getByIds(cardId: Long, levelId: Long) = dao.getByIds(cardId, levelId).toModel()
    suspend fun insert(user: CardInLevelModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: CardInLevelModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: CardInLevelModel) = baseRepo.delete(user.toEntity())
}