package bx.app.ui.navigation.data

import bx.app.ui.navigation.item.NavigationItem

internal object NavigationBarItems {
    private val deckItems = listOf<NavigationItem>(
        NavigationItem("Settings", NavigationRoute.DeckSettings(DatabaseOperation.UPDATE.toString()), NavigationRoute.Decks),
        NavigationItem("Cards", NavigationRoute.DeckCards(DatabaseOperation.UPDATE.toString()), NavigationRoute.Decks),
        NavigationItem("Level", NavigationRoute.DeckLevels(DatabaseOperation.UPDATE.toString()), NavigationRoute.Decks),
        NavigationItem("Learn", NavigationRoute.DeckLearn(DatabaseOperation.UPDATE.toString()), NavigationRoute.Decks),
    )

    private val cardItems = listOf<NavigationItem>(
        NavigationItem("Front", NavigationRoute.CardFront(DatabaseOperation.UPDATE.toString()), NavigationRoute.DeckCards()),
        NavigationItem("Back", NavigationRoute.CardBack(DatabaseOperation.UPDATE.toString()), NavigationRoute.DeckCards()),
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