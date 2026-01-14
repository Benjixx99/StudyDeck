package bx.app.data.local.mock

import bx.app.data.enums.CardFailing
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.data.enums.IntervalType
import bx.app.data.model.AudioSideModel
import bx.app.data.model.CardInLevelModel
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
        // card 1
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 1,
            backSideType = CardSideType.TEXT,
            backSideId = 2,
            deckId = 1,
        ),
        // card 2
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 3,
            backSideType = CardSideType.TEXT,
            backSideId = 4,
            deckId = 1,
        ),
        // card 3
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 5,
            backSideType = CardSideType.TEXT,
            backSideId = 6,
            deckId = 1,
        ),
        // card 4
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 7,
            backSideType = CardSideType.AUDIO,
            backSideId = 1,
            deckId = 2,
        ),
        // card 5
        CardModel(
            frontSideType = CardSideType.TEXT,
            frontSideId = 8,
            backSideType = CardSideType.TEXT,
            backSideId = 9,
            deckId = 1,
        ),
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
        ),
        TextSideModel(
            id = 8,
            text = "allow",
            side = CardSide.FRONT,
            cardId = 5
        ),
        TextSideModel(
            id = 9,
            text = "erlauben",
            side = CardSide.BACK,
            cardId = 5
        ),
    )
    val audioSide = listOf<AudioSideModel>(
        AudioSideModel(
            id = 1,
            fileName = "Test Audio",
            path = "test/path",
            side = CardSide.BACK,
            cardId = 4
        )
    )
    val levels = listOf<LevelModel>(
        LevelModel(
            name = "1. Level: every day",
            intervalNumber = 1,
            intervalType = IntervalType.DAY,
            deckId = 1
        ),
        LevelModel(
            name = "2. Level: every fourth day",
            intervalNumber = 4,
            intervalType = IntervalType.DAY,
            deckId = 1
        ),
        LevelModel(
            name = "3. Level: once a week",
            intervalNumber = 1,
            intervalType = IntervalType.WEEK,
            deckId = 1
        ),
        LevelModel(
            name = "4. Level: every two weeks",
            intervalNumber = 2,
            intervalType = IntervalType.WEEK,
            deckId = 1
        ),
        LevelModel(
            name = "5. Level: once a month",
            intervalNumber = 1,
            intervalType = IntervalType.MONTH,
            deckId = 1
        )
    )
    val cardInLevel = listOf<CardInLevelModel>(
        CardInLevelModel(
            cardId = 1,
            levelId = 1,
        ),
        CardInLevelModel(
            cardId = 2,
            levelId = 1,
        ),
        CardInLevelModel(
            cardId = 3,
            levelId = 1,
        ),
        CardInLevelModel(
            cardId = 5,
            levelId = 2,
        ),
    )
}