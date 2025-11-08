package bx.app.ui.helper

import androidx.compose.runtime.Composable
import bx.app.ui.composable.MainDropdownMenu
import bx.app.ui.navigation.item.NavigationItem
import bx.app.ui.navigation.data.NavigationBarItems
import bx.app.ui.navigation.data.NavigationRoutes

// TODO: Add this functions to a class or object!
@Composable
internal fun GetTopBarMenuAction(route: String): Unit {
    return when(route) {
        NavigationRoutes.DECKS -> {
            MainDropdownMenu()
        }
        else -> {  }
    }
}

/**
 * Get the corresponding navigation item list for the passed route
 */
internal fun getNavigationItemList(route: String): List<NavigationItem> {
    return if (route == NavigationRoutes.DECK_CARDS || route == NavigationRoutes.DECK_SETTINGS
        || route == NavigationRoutes.DECK_LEVELS || route == NavigationRoutes.DECK_LEARN) NavigationBarItems.deckItems
    else if (route == NavigationRoutes.CARD_FRONT || route == NavigationRoutes.CARD_BACK) NavigationBarItems.cardItems
    else NavigationBarItems.deckItems
}