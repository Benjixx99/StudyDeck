package bx.app.data.local.mock

import bx.app.data.enums.CardFailing
import bx.app.data.enums.CardSideType
import bx.app.data.model.CardModel
import bx.app.data.model.DeckModel
import bx.app.data.model.LevelModel

object DatabaseMockData {
    val decks = listOf<DeckModel>(
        DeckModel(
            name = "English",
            description =  "german to english",
            color =  0xFFFF0000,
            learnBothSides =  true,
            onFailing =  CardFailing.MOVE_TO_START
        ),
        DeckModel(
            name = "Italian",
            description = "english to italian",
            color = 0xFF00FF00,
            learnBothSides = true,
            onFailing = CardFailing.MOVE_ONE_LEVE_DOWN
        ),
        DeckModel(
            name = "Datastructures",
            description = "Learn about important datastructures for programming",
            color = 0xFF0000FF,
            learnBothSides = false,
            onFailing = CardFailing.STAY_ON_CURRENT_LEVEL
        ),
    )
    val cards = listOf<CardModel>(
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 1,
            backSideType = CardSideType.TEXT,
            backSideId = 2,
            deckId = 1,
        ),
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 3,
            backSideType = CardSideType.TEXT,
            backSideId = 4,
            deckId = 1,
        ),
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 5,
            backSideType = CardSideType.TEXT,
            backSideId = 6,
            deckId = 1,
        ),
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 7,
            backSideType = CardSideType.AUDIO,
            backSideId = 8,
            deckId = 1,
        )
    )

    val levels = listOf<LevelModel>(
        LevelModel(
            intervalNumber = 2,
            intervalType = 1,
            deckId = 1
        ),
        LevelModel(
            intervalNumber = 4,
            intervalType = 1,
            deckId = 1
        ),
        LevelModel(
            intervalNumber = 6,
            intervalType = 1,
            deckId = 1
        )
    )
}