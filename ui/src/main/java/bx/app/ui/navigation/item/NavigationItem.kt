package bx.app.ui.navigation.item

import bx.app.ui.navigation.data.NavigationRoute

data class NavigationItem(
    val text: String,
    val route: NavigationRoute,
    val backStackRoute: NavigationRoute,
)