package bx.app.data.repository

import bx.app.data.enums.CardFailing
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.model.CardInLevelModel

class CardInLevelRepository(private val database: AppDatabase) {
    private val cardInLevelDao = database.cardInLevelDao()
    private val baseRepo = BaseRepository<CardInLevelEntity>(cardInLevelDao)

    suspend fun getById(id: Long) = baseRepo.getById(id) as CardInLevelModel
    suspend fun insert(cardInLevel: CardInLevelModel) = baseRepo.insert(cardInLevel.toEntity())

    suspend fun update(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
    ) = cardInLevelDao.update(levelId, cardId, learnBothSides, database)

    suspend fun update(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
        onFailing: CardFailing,
    ) = cardInLevelDao.update(levelId, cardId, learnBothSides, onFailing, database)

    suspend fun delete(cardInLevel: CardInLevelModel) = baseRepo.delete(cardInLevel.toEntity())

    fun getLastTimeLearnedFrontByCardId(id: Long) = cardInLevelDao.getLastTimeLearnedFrontByCardId(id)
    fun countCardsByLevelId(id: Long) = cardInLevelDao.countCardsByLevelId(id)
    fun countLearnableCardsByLevelId(id: Long) = cardInLevelDao.countLearnableCardsByLevelId(id)
}