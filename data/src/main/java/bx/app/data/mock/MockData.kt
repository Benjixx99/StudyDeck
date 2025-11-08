package bx.app.data.mock

import bx.app.data.mock.item.AudioCardSide
import bx.app.data.mock.item.CardItem
import bx.app.data.mock.item.CardSide.CardType
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
            TextCardSide(CardType.Text, "Hello"),
            TextCardSide(CardType.Text, "Hallo")
        ),
        CardItem(
            2,
            "Second Card",
            "Not important",
            TextCardSide(CardType.Text, "Bye"),
            TextCardSide(CardType.Text, "Aufwiedersehen")
        ),
        CardItem(
            3,
            "",
            "",
            TextCardSide(CardType.Text, "Boy"),
            TextCardSide(CardType.Text, "Junge")
        ),
        CardItem(
            4,
            "",
            "",
            TextCardSide(CardType.Text, "Girl"),
            TextCardSide(CardType.Text, "Mädchen")
        ),
        CardItem(
            5,
            "",
            "",
            TextCardSide(CardType.Text, "Something differently"),
            AudioCardSide(CardType.Audio, "Path/to/some/audio/file")
        ),
        CardItem(
            6,
            "",
            "",
            AudioCardSide(CardType.Audio, "PATH"),
            AudioCardSide(CardType.Audio, "Another/path/to/another/file")
        ),
        CardItem(
            7,
            "",
            "",
            TextCardSide(
                CardType.Text,
                "Very very long long text that is too long for the field but it can fit into 2 lines"
            ),
            TextCardSide(CardType.Text, "Not to long")
        ),
        CardItem(
            8,
            "",
            "",
            TextCardSide(CardType.Text, "available"),
            TextCardSide(
                CardType.Text,
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