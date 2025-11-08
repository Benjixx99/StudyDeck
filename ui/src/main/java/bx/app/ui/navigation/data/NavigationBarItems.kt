package bx.app.ui.navigation.data

import bx.app.ui.navigation.item.NavigationItem

internal object NavigationBarItems {
    val deckItems = listOf<NavigationItem>(
        NavigationItem("Settings", NavigationRoutes.DECK_SETTINGS, NavigationRoutes.DECKS),
        NavigationItem("Cards", NavigationRoutes.DECK_CARDS, NavigationRoutes.DECKS),
        NavigationItem("Level", NavigationRoutes.DECK_LEVELS, NavigationRoutes.DECKS),
        NavigationItem("Learn", NavigationRoutes.DECK_LEARN, NavigationRoutes.DECKS),
    )

    val cardItems = listOf<NavigationItem>(
        NavigationItem("Front", NavigationRoutes.CARD_FRONT, NavigationRoutes.DECK_CARDS),
        NavigationItem("Back", NavigationRoutes.CARD_BACK, NavigationRoutes.DECK_CARDS),
    )
}