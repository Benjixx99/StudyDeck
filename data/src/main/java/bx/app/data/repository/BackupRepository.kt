package bx.app.data.repository

import bx.app.core.maxLength
import bx.app.data.backup.BackupBundle
import bx.app.data.enums.CardSideType
import bx.app.data.local.AppDatabase
import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardInLevelModel
import bx.app.data.model.CardModel
import bx.app.data.model.DeckModel
import bx.app.data.model.LevelModel
import bx.app.data.model.TextSideModel

class BackupRepository(private val database: AppDatabase) {
    suspend fun exportAll(): BackupBundle {
        return BackupBundle(
            appName = "Study Deck",
            version = 1,
            decks = database.deckDao().getAll().map { it.toModel() },
            cards = database.cardDao().getAll().map { it.toModel() },
            levels = database.levelDao().getAll().map { it.toModel() },
            cardInLevels = database.cardInLevelDao().getAll().map { it.toModel() },
            textSides = database.textSideDao().getAll().map { it.toModel() },
            audioSides = database.audioSideDao().getAll().map { it.toModel() }
        )
    }

    suspend fun importAll(backupBundle: BackupBundle) {
        val deckIdMap = importDecks(backupBundle.decks)
        val cardIdMap = importCards(backupBundle.cards, backupBundle.textSides, backupBundle.audioSides, deckIdMap)
        val levelIdMap = importLevels(backupBundle.levels, deckIdMap)
        importCardInLevels(backupBundle.cardInLevels, cardIdMap, levelIdMap)
    }

    private suspend fun importDecks(decks: List<DeckModel>): Map<Long, Long> {
        val deckIds = mutableMapOf<Long, Long>()
        for (deck in decks) {
            deckIds.put(
                key = deck.id,
                value = database.deckDao().insert(deck.copy(
                    id = 0,
                    name = deck.name.maxLength(100),
                    description = deck.description?.maxLength(100)
                ).toEntity()))
        }
        return deckIds
    }

    private suspend fun importCards(
        cards: List<CardModel>,
        textSides: List<TextSideModel>,
        audioSides: List<AudioSideModel>,
        deckIdMap: Map<Long, Long>
    ): Map<Long, Long> {
        val cardIdMap = mutableMapOf<Long, Long>()
        val oldCardTextSideIds = mutableListOf<Long>()
        val oldCardAudioSideIds = mutableListOf<Long>()
        val oldTextSideIds = mutableListOf<Long>()
        val oldAudioSideIds = mutableListOf<Long>()

        textSides.forEach { oldTextSideIds.add(it.id) }
        audioSides.forEach { oldAudioSideIds.add(it.id) }

        for (card in cards) {
            when (card.frontSideType) {
                CardSideType.TEXT -> if (!oldTextSideIds.contains(card.frontSideId)) continue
                CardSideType.AUDIO -> if (!oldAudioSideIds.contains(card.frontSideId)) continue
            }
            when (card.backSideType) {
                CardSideType.TEXT -> if (!oldTextSideIds.contains(card.backSideId)) continue
                CardSideType.AUDIO -> if (!oldAudioSideIds.contains(card.backSideId)) continue
            }

            val deckId = deckIdMap[card.deckId] ?: continue
            cardIdMap.put(card.id, database.cardDao().insert(card.copy(id = 0, deckId = deckId).toEntity()))

            when (card.frontSideType) {
                CardSideType.TEXT -> oldCardTextSideIds.add(card.frontSideId)
                CardSideType.AUDIO -> oldCardAudioSideIds.add(card.frontSideId)
            }
            when (card.backSideType) {
                CardSideType.TEXT -> oldCardTextSideIds.add(card.backSideId)
                CardSideType.AUDIO -> oldCardAudioSideIds.add(card.backSideId)
            }
        }

        for (textSide in textSides) {
            val newCardId = cardIdMap[textSide.cardId] ?: continue
            val newNextSideId = database.textSideDao().insert(textSide.copy(cardId = newCardId).toEntity())

            if (!oldCardTextSideIds.contains(textSide.id)) continue
            database.cardDao().update(
                textSide.side.setTextSide(database.cardDao().getById(newCardId), newNextSideId)
            )
        }

        for (audioSide in audioSides) {
            val newCardId = cardIdMap[audioSide.cardId] ?: continue
            val newAudioSideId = database.audioSideDao().insert(audioSide.copy(cardId = newCardId).toEntity())

            if (!oldCardAudioSideIds.contains(audioSide.id)) continue
            database.cardDao().update(
                audioSide.side.setAudioSide(database.cardDao().getById(newCardId), newAudioSideId)
            )
        }
        return cardIdMap
    }

    private suspend fun importLevels(
        levels: List<LevelModel>,
        deckIdMap: Map<Long, Long>
    ): Map<Long, Long> {
        val levelIdMap = mutableMapOf<Long, Long>()

        for (level in levels) {
            val newDeckId = deckIdMap[level.deckId] ?: continue
            val exists = database.levelDao()
                .existsIntervalByDeckId(newDeckId, 0, level.intervalNumber, level.intervalType)

            if (exists) continue
            levelIdMap.put(
                key = level.id,
                value = database.levelDao().insert(level.copy(
                    id = 0,
                    name = level.name.maxLength(100),
                    deckId = newDeckId
                ).toEntity())
            )
        }
        return levelIdMap
    }

    private suspend fun importCardInLevels(
        cardInLevels: List<CardInLevelModel>,
        cardIdMap: Map<Long, Long>,
        levelIdMap: Map<Long, Long>
    ) {
        for (cardInLevel in cardInLevels) {
            val newCardId = cardIdMap[cardInLevel.cardId] ?: continue
            val newLevelId = levelIdMap[cardInLevel.levelId] ?: continue

            database.cardInLevelDao().insert(
                cardInLevel.copy(cardId = newCardId, levelId = newLevelId).toEntity()
            )
        }
    }
}