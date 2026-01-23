package bx.app.data.backup

import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardInLevelModel
import bx.app.data.model.CardModel
import bx.app.data.model.DeckModel
import bx.app.data.model.LevelModel
import bx.app.data.model.TextSideModel
import kotlinx.serialization.Serializable

@Serializable
data class BackupBundle(
    val appName: String,
    val version: Int,
    val decks: List<DeckModel> = emptyList(),
    val cards: List<CardModel> = emptyList(),
    val levels: List<LevelModel> = emptyList(),
    val textSides: List<TextSideModel> = emptyList(),
    val audioSides: List<AudioSideModel> = emptyList(),
    val cardInLevels: List<CardInLevelModel> = emptyList(),
)