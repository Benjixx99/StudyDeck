package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.local.entity.CardEntity
import bx.app.data.local.entity.CardInLevelEntity
import bx.app.data.local.entity.TextSideEntity

@Dao
internal interface CardWithSidesDao : CardDao {
    @Transaction
    suspend fun updateCardSide(
        card: CardEntity,
        textSide: TextSideEntity,
        audioSide: AudioSideEntity,
        cardSideType: CardSideType,
        cardSide: CardSide,
        database: AppDatabase,
    ): Long {
        val id = when (cardSideType) {
            CardSideType.TEXT -> database.textSideDao().getTextSideIdByCardId(card.id, cardSide)
            CardSideType.AUDIO -> database.audioSideDao().getAudioSideIdByCardId(card.id, cardSide)
        }
        if (id == null) return 0L

        when (cardSide) {
            CardSide.FRONT -> update(card.copy(frontSideType = cardSideType, frontSideId = id))
            CardSide.BACK -> update(card.copy(backSideType = cardSideType, backSideId = id))
        }
        return id
    }

    @Transaction
    suspend fun insertCardWithTextSide(
        card: CardEntity,
        textSide: TextSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ): Pair<Long, Long> {
        val cardId = insert(card)
        val textSideId = database.textSideDao().insert(textSide.copy(cardId = cardId, side = cardSide))
        database.cardInLevelDao().insert(
            CardInLevelEntity(
                cardId = cardId,
                levelId = database.levelDao().getFirstByDeckId(card.deckId)
            )
        )
        update(cardSide.setTextSide(getById(cardId), textSideId))
        return Pair(cardId, textSideId)
    }

    @Transaction
    suspend fun insertTextSideAndUpdateCard(
        card: CardEntity,
        textSide: TextSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ): Long {
        val textSideId = database.textSideDao().insert(textSide.copy(side = cardSide))
        update(cardSide.setTextSide(getById(card.id), textSideId))
        return textSideId
    }

    @Transaction
    suspend fun updateCardWithTextSide(
        card: CardEntity,
        textSide: TextSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ) {
        database.textSideDao().update(textSide)
        update(cardSide.setTextSide(card, textSide.id))
    }

    @Transaction
    suspend fun insertCardWithAudioSide(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ): Pair<Long, Long> {
        val cardId = insert(card)
        val audioSideId = database.audioSideDao().insert(audioSide.copy(cardId = cardId, side = cardSide))
        database.cardInLevelDao().insert(
            CardInLevelEntity(
                cardId = cardId,
                levelId = database.levelDao().getFirstByDeckId(card.deckId)
            )
        )
        update(cardSide.setAudioSide(getById(cardId), audioSideId))
        return Pair(cardId, audioSideId)
    }

    @Transaction
    suspend fun insertAudioSideAndUpdateCard(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ): Long {
        val audioSideId = database.audioSideDao().insert(audioSide.copy(side = cardSide))
        update(cardSide.setAudioSide(getById(card.id), audioSideId))
        return audioSideId
    }

    @Transaction
    suspend fun updateCardWithAudioSide(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
        database: AppDatabase,
    ) {
        database.audioSideDao().update(audioSide)
        update(cardSide.setAudioSide(card, audioSide.id))
    }
}