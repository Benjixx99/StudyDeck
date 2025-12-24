package bx.app.ui.navigation.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import bx.app.presentation.viewmodel.DeckViewModel
import bx.app.ui.navigation.item.NavigationItem
import androidx.compose.runtime.getValue
import bx.app.presentation.viewmodel.CardViewModel

internal object NavigationBarItems {
    private val deckItems = listOf<NavigationItem>(
        NavigationItem("Settings", NavigationRoute.DeckSettings("-2"), NavigationRoute.Decks),
        NavigationItem("Cards", NavigationRoute.DeckCards("-2"), NavigationRoute.Decks),
        NavigationItem("Level", NavigationRoute.DeckLevels("-2"), NavigationRoute.Decks),
        NavigationItem("Learn", NavigationRoute.DeckLearn("-2"), NavigationRoute.Decks),
    )

    private val cardItems = listOf<NavigationItem>(
        NavigationItem("Front", NavigationRoute.CardFront("-2"), NavigationRoute.DeckCards("-2")),
        NavigationItem("Back", NavigationRoute.CardBack("-2"), NavigationRoute.DeckCards("-2")),
    )

    @Composable
    fun SetDeckId(deckViewModel: DeckViewModel) {
        val deck by deckViewModel.deck.collectAsState()
        deckItems.forEach { (it.route as NavigationRoute.WithId).id = deck.id.toString() }
        cardItems.forEach { (it.backStackRoute as NavigationRoute.WithId).id = deck.id.toString() }
    }

    @Composable
    fun SetCardId(cardViewModel: CardViewModel) {
        val card by cardViewModel.card.collectAsState()
        cardItems.forEach { (it.route as NavigationRoute.WithId).id = card.id.toString() }
    }

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