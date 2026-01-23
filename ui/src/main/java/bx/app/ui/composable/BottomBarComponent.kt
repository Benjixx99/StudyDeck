package bx.app.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bx.app.ui.navigation.data.NavigationBarItems
import bx.app.ui.navigation.data.NavigationRoute
import bx.app.ui.navigation.item.NavigationItem
import bx.app.ui.navigation.navigateWithSettingBackStack

object BottomBarComponent {
    @Composable
    fun Manager(hide: Boolean, navHostController: NavHostController) {
        val currentBackStack by navHostController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentRoute = NavigationRoute.getCurrentNavigationRoute(currentDestination?.route)

        if (hide) return

        // TODO: Probably I need a function like DisplayBottomNavigationBar()
        if (currentRoute is NavigationRoute.Decks || currentRoute is NavigationRoute.Level
            || currentRoute is NavigationRoute.RandomLearningPhase
            || currentRoute is NavigationRoute.LevelLearningPhase
            || currentRoute is NavigationRoute.LearnLevel) return

        val items = NavigationBarItems.getNavigationItemList(currentRoute)

        BottomBar(
            items = items,
            onItemSelected = { navItem -> navHostController.navigateWithSettingBackStack(navItem.route, navItem.backStackRoute) },
            currentItem = items.find { it.route.sameRoute(currentRoute) } ?: items[0]
        )
    }

    @Composable
    private fun BottomBar(
        items: List<NavigationItem>,
        onItemSelected: (NavigationItem) -> Unit = {},
        currentItem: NavigationItem
    ) {
        NavigationBar(items, onItemSelected, currentItem)
    }
}