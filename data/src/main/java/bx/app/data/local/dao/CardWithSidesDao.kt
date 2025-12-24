package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.local.entity.CardEntity
import bx.app.data.local.entity.TextSideEntity

@Dao
internal interface CardWithSidesDao : CardDao {
    @Query("SELECT id FROM text_side WHERE card_id = :id AND side = :cardSide")
    fun getTextSideIdByCardId(id: Long, cardSide: CardSide): Long?

    @Query("SELECT id FROM audio_side WHERE card_id = :id AND side = :cardSide")
    fun getAudioSideIdByCardId(id: Long, cardSide: CardSide): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(textSide: TextSideEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(textSide: TextSideEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audioSide: AudioSideEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(audioSide: AudioSideEntity)

    @Transaction
    suspend fun updateCardSide(
        card: CardEntity,
        textSide: TextSideEntity,
        audioSide: AudioSideEntity,
        cardSideType: CardSideType,
        cardSide: CardSide,
    ): Long {
        val id = when (cardSideType) {
            CardSideType.TEXT -> getTextSideIdByCardId(card.id, cardSide)
            CardSideType.AUDIO -> getAudioSideIdByCardId(card.id, cardSide)
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
    ): Pair<Long, Long> {
        val cardId = insert(card)
        val textSideId = insert(textSide.copy(cardId = cardId, side = cardSide))
        update(cardSide.setTextSide(getById(cardId), textSideId))
        return Pair(cardId, textSideId)
    }

    @Transaction
    suspend fun insertTextSideAndUpdateCard(
        card: CardEntity,
        textSide: TextSideEntity,
        cardSide: CardSide,
    ): Long {
        val textSideId = insert(textSide.copy(side = cardSide))
        update(cardSide.setTextSide(getById(card.id), textSideId))
        return textSideId
    }

    @Transaction
    suspend fun updateCardWithTextSide(
        card: CardEntity,
        textSide: TextSideEntity,
        cardSide: CardSide,
    ) {
        update(textSide)
        update(cardSide.setTextSide(card, textSide.id))
    }

    @Transaction
    suspend fun insertCardWithAudioSide(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
    ): Pair<Long, Long> {
        val cardId = insert(card)
        val audioSideId = insert(audioSide.copy(cardId = cardId, side = cardSide))
        update(cardSide.setAudioSide(getById(cardId), audioSideId))
        return Pair(cardId, audioSideId)
    }

    @Transaction
    suspend fun insertAudioSideAndUpdateCard(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
    ): Long {
        val audioSideId = insert(audioSide.copy(side = cardSide))
        update(cardSide.setAudioSide(getById(card.id), audioSideId))
        return audioSideId
    }

    @Transaction
    suspend fun updateCardWithAudioSide(
        card: CardEntity,
        audioSide: AudioSideEntity,
        cardSide: CardSide,
    ) {
        update(audioSide)
        update(cardSide.setAudioSide(card, audioSide.id))
    }

    fun CardSide.setTextSide(card: CardEntity, id: Long): CardEntity = when (this) {
        CardSide.FRONT -> card.copy(frontSideType = CardSideType.TEXT, frontSideId = id)
        CardSide.BACK -> card.copy(backSideType = CardSideType.TEXT, backSideId = id)
    }

    fun CardSide.setAudioSide(card: CardEntity, id: Long): CardEntity = when (this) {
        CardSide.FRONT -> card.copy(frontSideType = CardSideType.AUDIO, frontSideId = id)
        CardSide.BACK -> card.copy(backSideType = CardSideType.AUDIO, backSideId = id)
    }
}