package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import bx.app.data.enums.CardFailing
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.CardInLevelEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
internal interface CardInLevelDao : BaseDao<CardInLevelEntity> {
    @Query("SELECT * FROM card_in_level")
    override fun observeAll(): Flow<List<CardInLevelEntity>>

    @Query("SELECT * FROM card_in_level")
    override suspend fun getAll(): List<CardInLevelEntity>

    // TODO: This query is useless because the primary key consists of level id and card id
    @Query("SELECT * FROM card_in_level WHERE level_id = :id")
    override suspend fun getById(id: Long): CardInLevelEntity

    @Query("SELECT * FROM card_in_level WHERE card_id = :id")
    suspend fun getByCardId(id: Long): CardInLevelEntity

    @Query("DELETE FROM card_in_level")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM card_in_level WHERE card_id = :cardId AND level_id = :levelId")
    fun countById(cardId: Long, levelId: Long): Int

    @Query("SELECT last_time_Learned_front FROM card_in_level WHERE card_id = :cardId")
    fun getLastTimeLearnedFrontByCardId(cardId: Long): Flow<Boolean>

    @Query("SELECT count(*) FROM card_in_level WHERE level_id = :levelId")
    fun countCardsByLevelId(levelId: Long): Flow<Int?>

    @Query("""
        SELECT count(*) 
        FROM card_in_level AS cil
        JOIN level AS l ON l.id = cil.level_id
        WHERE cil.level_id = :levelId 
        AND (
            cil.last_time_learned_date IS NULL
            OR (strftime('%s','now') - (cil.last_time_learned_date / 1000.0)) 
                / 86400.0 >= l.interval_number * l.interval_type
        ) 
    """)
    fun countLearnableCardsByLevelId(levelId: Long): Flow<Int?>


    @Query("""
        UPDATE card_in_level SET 
        level_id = :levelId,
        last_time_Learned_front = :lastTimeLearnedFront,
        last_time_Learned_date = :lastTimeLearnedDate
        WHERE card_id = :cardId
    """)
    suspend fun update(
        cardId: Long,
        levelId: Long,
        lastTimeLearnedFront: Boolean,
        lastTimeLearnedDate: LocalDateTime = LocalDateTime.now()
    )

    @Transaction
    suspend fun update(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
        database: AppDatabase,
    ) {
        val levelDao = database.levelDao()
        val level = levelDao.getById(levelId)
        val intervalInDays = level.intervalNumber * level.intervalType.days
        updateHelper(
            cardInLevel = getByCardId(cardId),
            newLevelId = levelDao.getNextLevelId(intervalInDays, level.deckId),
            learnBothSides = learnBothSides,
        )
    }

    @Transaction
    suspend fun update(
        cardId: Long,
        levelId: Long,
        learnBothSides: Boolean,
        onFailing: CardFailing,
        database: AppDatabase,
    ) {
        val levelDao = database.levelDao()
        val level = levelDao.getById(levelId)
        val intervalInDays = level.intervalNumber * level.intervalType.days
        updateHelper(
            cardInLevel = getByCardId(cardId),
            newLevelId = when (onFailing) {
                CardFailing.MOVE_ONE_LEVE_DOWN -> levelDao.getPriorLevelId(intervalInDays, level.deckId)
                CardFailing.MOVE_TO_START -> levelDao.getFirstByDeckId(level.deckId)
                CardFailing.STAY_ON_CURRENT_LEVEL -> null
            },
            learnBothSides = learnBothSides
        )
    }

    private suspend fun updateHelper(
        cardInLevel: CardInLevelEntity,
        newLevelId: Long?,
        learnBothSides: Boolean,
    ) {
        update(
            cardId = cardInLevel.cardId,
            levelId = when {
                !learnBothSides || cardInLevel.lastTimeLearnedFront -> newLevelId ?: cardInLevel.levelId
                else -> cardInLevel.levelId
            },
            lastTimeLearnedFront = when {
                !learnBothSides -> cardInLevel.lastTimeLearnedFront
                else -> !cardInLevel.lastTimeLearnedFront
            },
        )
    }
}