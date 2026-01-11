package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.model.CardInLevelModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardInLevelRepository(database: AppDatabase) {
    private val cardInLevelDao = database.cardInLevelDao()
    private val baseRepo = BaseRepository<CardInLevelEntity>(cardInLevelDao)

    fun getAll(): Flow<List<CardInLevelModel>> = baseRepo.flowList.map { it.filterIsInstance<CardInLevelModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as CardInLevelModel
    suspend fun getByIds(cardId: Long, levelId: Long) = cardInLevelDao.getByIds(cardId, levelId).toModel()
    suspend fun insert(cardInLevel: CardInLevelModel) = baseRepo.insert(cardInLevel.toEntity())
    suspend fun update(cardInLevel: CardInLevelModel) = baseRepo.update(cardInLevel.toEntity())
    suspend fun delete(cardInLevel: CardInLevelModel) = baseRepo.delete(cardInLevel.toEntity())

    fun countCardsByLevelId(id: Long) = cardInLevelDao.countCardsByLevelId(id)
}