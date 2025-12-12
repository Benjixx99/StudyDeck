package bx.app.data.local.mock

import bx.app.data.enums.CardFailing
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardModel
import bx.app.data.model.DeckModel
import bx.app.data.model.LevelModel
import bx.app.data.model.TextSideModel

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
            backSideId = 1,
            deckId = 2,
        )
    )

    val textSide = listOf<TextSideModel>(
        TextSideModel(
            id = 1,
            text = "Boy",
            side = CardSide.FRONT,
            cardId = 1
        ),
        TextSideModel(
            id = 2,
            text = "Junge",
            side = CardSide.BACK,
            cardId = 1
        ),
        TextSideModel(
            id = 3,
            text = "Girl",
            side = CardSide.FRONT,
            cardId = 2
        ),
        TextSideModel(
            id = 4,
            text = "Mädchen",
            side = CardSide.BACK,
            cardId = 2
        ),
        TextSideModel(
            id = 5,
            text = "incredible",
            side = CardSide.FRONT,
            cardId = 3
        ),
        TextSideModel(
            id = 6,
            text = "unglaublich",
            side = CardSide.BACK,
            cardId = 3
        ),
        TextSideModel(
            id = 7,
            text = "You can do it",
            side = CardSide.FRONT,
            cardId = 4
        )
    )

    val audioSide = listOf<AudioSideModel>(
        AudioSideModel(
            id = 1,
            fileName = "Test Audio",
            path = "test/path",
            side = CardSide.BACK,
            cardId = 3
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