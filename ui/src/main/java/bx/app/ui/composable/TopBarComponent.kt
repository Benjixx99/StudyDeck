package bx.app.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bx.app.ui.navigation.data.NavigationRoute

object TopBarComponent {
    @Composable
    fun Manager(title: String, navHostController: NavHostController) {
        val currentBackStack by navHostController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentRoute = NavigationRoute.getCurrentNavigationRoute(currentDestination?.route)

        TopBar(
            title = title,
            onBackClick = {
                if (currentRoute !is NavigationRoute.Decks) navHostController.popBackStack()
            },
            actions = { GetTopBarMenuAction(currentRoute) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(
        title: String,
        onBackClick: () -> Unit,
        actions: @Composable (RowScope.() -> Unit) = {}
    ) {
        // TODO: Display this info only while developing
        val context = LocalContext.current
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName

        TopAppBar(
            title = { Text(text = "$title - $versionName DEV", fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Companion.White
                    )
                }
            },
            actions = actions
        )
    }

    @Composable
    private fun GetTopBarMenuAction(route: NavigationRoute) {
        return when(route) {
            is NavigationRoute.Decks -> {
                MainDropdownMenu()
            }
            else -> {  }
        }
    }
}
