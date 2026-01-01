package bx.app.data.repository

import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.local.AppDatabase
import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardModel
import bx.app.data.model.TextSideModel

class CardWithSidesRepository(database: AppDatabase) {
    private val dao = database.cardWithSidesDao()

    suspend fun deleteCardsByDeckId(id: Long) = dao.deleteCardsByDeckId(id)

    suspend fun updateCardSide(
        card: CardModel,
        textSide: TextSideModel,
        audioSide: AudioSideModel,
        cardSideType: CardSideType,
        cardSide: CardSide,
    ): Long = dao.updateCardSide(
        card = card.toEntity(),
        textSide = textSide.toEntity(),
        audioSide = audioSide.toEntity(),
        cardSideType = cardSideType,
        cardSide = cardSide
    )

    suspend fun insertCardWithTextSide(
        card: CardModel,
        textSide: TextSideModel,
        cardSide: CardSide
    ): Pair<Long, Long> = dao.insertCardWithTextSide(card.toEntity(), textSide.toEntity(), cardSide)

    suspend fun insertTextSideAndUpdateCard(
        card: CardModel,
        textSide: TextSideModel,
        cardSide: CardSide
    ): Long = dao.insertTextSideAndUpdateCard(card.toEntity(), textSide.toEntity(), cardSide)

    suspend fun updateCardWithTextSide(
        card: CardModel,
        textSide: TextSideModel,
        cardSide: CardSide
    ) = dao.updateCardWithTextSide(card.toEntity(), textSide.toEntity(), cardSide)

    suspend fun insertCardWithAudioSide(
        card: CardModel,
        audioSide: AudioSideModel,
        cardSide: CardSide
    ): Pair<Long, Long> = dao.insertCardWithAudioSide(card.toEntity(), audioSide.toEntity(), cardSide)

    suspend fun insertAudioSideAndUpdateCard(
        card: CardModel,
        audioSide: AudioSideModel,
        cardSide: CardSide
    ): Long = dao.insertAudioSideAndUpdateCard(card.toEntity(), audioSide.toEntity(), cardSide)

    suspend fun updateCardWithAudioSide(
        card: CardModel,
        audioSide: AudioSideModel,
        cardSide: CardSide
    ) = dao.updateCardWithAudioSide(card.toEntity(), audioSide.toEntity(), cardSide)
}