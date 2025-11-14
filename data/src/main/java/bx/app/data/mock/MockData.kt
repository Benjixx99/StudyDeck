package bx.app.data.mock

import bx.app.data.enums.CardType
import bx.app.data.mock.item.AudioCardSide
import bx.app.data.mock.item.CardItem
import bx.app.data.mock.item.DeckItem
import bx.app.data.mock.item.LevelItem
import bx.app.data.mock.item.TextCardSide

object MockData {
    val deckItems = listOf<DeckItem>(
        DeckItem(1, "English", "English to german words", 50, true),
        DeckItem(2, "Italian", "Italian to english words", 20, false),
        DeckItem(3, "Datastructures", "Learn about important datastructures for programming ", 6, true),
        DeckItem(4, "OOB", "Object orientated programming", 15, true),
        DeckItem(5, "Android mobile programming", "", 20, false),
    )
    val cardItems = listOf<CardItem>(
        CardItem(
            1,
            "First Card",
            "Not important",
            TextCardSide(CardType.TEXT, "Hello"),
            TextCardSide(CardType.TEXT, "Hallo")
        ),
        CardItem(
            2,
            "Second Card",
            "Not important",
            TextCardSide(CardType.TEXT, "Bye"),
            TextCardSide(CardType.TEXT, "Aufwiedersehen")
        ),
        CardItem(
            3,
            "",
            "",
            TextCardSide(CardType.TEXT, "Boy"),
            TextCardSide(CardType.TEXT, "Junge")
        ),
        CardItem(
            4,
            "",
            "",
            TextCardSide(CardType.TEXT, "Girl"),
            TextCardSide(CardType.TEXT, "Mädchen")
        ),
        CardItem(
            5,
            "",
            "",
            TextCardSide(CardType.TEXT, "Something differently"),
            AudioCardSide(CardType.AUDIO, "Path/to/some/audio/file")
        ),
        CardItem(
            6,
            "",
            "",
            AudioCardSide(CardType.AUDIO, "PATH"),
            AudioCardSide(CardType.AUDIO, "Another/path/to/another/file")
        ),
        CardItem(
            7,
            "",
            "",
            TextCardSide(
                CardType.TEXT,
                "Very very long long text that is too long for the field but it can fit into 2 lines"
            ),
            TextCardSide(CardType.TEXT, "Not to long")
        ),
        CardItem(
            8,
            "",
            "",
            TextCardSide(CardType.TEXT, "available"),
            TextCardSide(
                CardType.TEXT,
                "verfügbar, erhältlich, lieferbar,\nvorrätig, erreichbar"
            )
        ),
    )
    val levelItems = listOf<LevelItem>(
        LevelItem(1, "Tier 1", "Every day", 0),
        LevelItem(2, "Tier 2", "Three times a week", 0),
        LevelItem(3, "Tier 3", "Once a week", 0),
        LevelItem(4, "Tier 4", "Every other week", 0),
        LevelItem(5, "Tier 5", "Once a month", 0),
    )
}