package bx.app.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bx.app.ui.helper.getNavigationItemList
import bx.app.ui.navigation.data.NavigationRoutes
import bx.app.ui.navigation.item.NavigationItem
import bx.app.ui.navigation.navigateWithSettingBackStack

object BottomBarComponent {
    @Composable
    fun Manager(navHostController: NavHostController) {
        val currentBackStack by navHostController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentRoute = NavigationRoutes.list.find { it == currentDestination?.route } ?: NavigationRoutes.DECKS

        // TODO: Probably I need a function like DisplayBottomNavigationBar() or GetNavigationBarItems()
        if (currentRoute != NavigationRoutes.DECKS && currentRoute != NavigationRoutes.LEVEL
            && currentRoute != NavigationRoutes.LEARN_RANDOM && currentRoute != NavigationRoutes.LEARN_LEVEL) {
            val items = getNavigationItemList(currentRoute)

            BottomBar(
                items = items,
                onItemSelected = { navItem -> navHostController.navigateWithSettingBackStack(navItem.route, navItem.backStackRoute) },
                currentItem = items.find { it.route == currentRoute } ?: items[0]
            )
        }
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