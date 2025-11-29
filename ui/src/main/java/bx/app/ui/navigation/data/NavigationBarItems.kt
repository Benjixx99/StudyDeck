package bx.app.ui.navigation.data

import bx.app.ui.navigation.item.NavigationItem

internal object NavigationBarItems {
    var deckId: String = "0"
    var cardId: String = "0"

    private val deckItems = listOf<NavigationItem>(
        NavigationItem("Settings", NavigationRoute.DeckSettings(deckId), NavigationRoute.Decks),
        NavigationItem("Cards", NavigationRoute.DeckCards(deckId), NavigationRoute.Decks),
        NavigationItem("Level", NavigationRoute.DeckLevels(deckId), NavigationRoute.Decks),
        NavigationItem("Learn", NavigationRoute.DeckLearn(deckId), NavigationRoute.Decks),
    )

    private val cardItems = listOf<NavigationItem>(
        NavigationItem("Front", NavigationRoute.CardFront(cardId), NavigationRoute.DeckCards(deckId)),
        NavigationItem("Back", NavigationRoute.CardBack(cardId), NavigationRoute.DeckCards(deckId)),
    )

    /**
     * Get the corresponding navigation item list for the passed route
     */
    internal fun getNavigationItemList(route: NavigationRoute): List<NavigationItem> {
        return if (route is NavigationRoute.DeckCards || route is NavigationRoute.DeckSettings
            || route is NavigationRoute.DeckLevels || route is NavigationRoute.DeckLearn) deckItems
        else if (route is NavigationRoute.CardFront || route is NavigationRoute.CardBack) cardItems
        else deckItems
    }
}