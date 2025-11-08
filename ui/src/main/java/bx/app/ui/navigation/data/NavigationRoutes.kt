package bx.app.ui.navigation.data

object NavigationRoutes {
    const val DECKS = "decks"

    private const val DECK = "deck"
    const val DECK_SETTINGS = DECK + "_settings"
    const val DECK_CARDS = DECK + "_cards"
    const val DECK_LEVELS = DECK + "_levels"
    const val DECK_LEARN = DECK + "_learn"

    private const val CARD = "card"
    const val CARD_FRONT = CARD + "_front"
    const val CARD_BACK = CARD + "_back"

    const val LEVEL = "level"

    private const val LEARN = "learn"
    const val LEARN_RANDOM = LEARN + "_random"
    const val LEARN_LEVEL = LEARN + "_level"
    const val LEARN_PHASE = LEARN + "_phase"

    val list = listOf(
        DECKS,
        DECK_CARDS,
        DECK_LEARN,
        DECK_SETTINGS,
        DECK_LEVELS,
        CARD_FRONT,
        CARD_BACK,
        LEVEL,
        LEARN_RANDOM,
        LEARN_LEVEL,
        LEARN_PHASE,
    )
}