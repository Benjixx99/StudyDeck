package bx.app.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navOptions

// TODO: Maybe add this file to a new module called ui-android-lib
/**
 * This extension function of [NavHostController] navigates to the passed route with launchSingleTop equal true
 */
internal fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) { launchSingleTop = true }

/**
 * This extension function of [NavHostController] navigates to the passed route and sets backStackRoute as the back stack route
 */
internal fun NavHostController.navigateWithSettingBackStack(route: String, backStackRoute: String) {
    this.navigate(
        route = route,
        navOptions = navOptions {
            popUpTo(backStackRoute)
            restoreState = true
        }
    )
}