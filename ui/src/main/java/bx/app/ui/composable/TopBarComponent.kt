package bx.app.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bx.app.data.enums.ConfigScope
import bx.app.data.enums.SortMode
import bx.app.presentation.enums.ImportMode
import bx.app.presentation.viewmodel.ConfigViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.navigation.data.NavigationRoute

object TopBarComponent {
    @Composable
    fun Manager(
        topBarViewModel: TopBarViewModel,
        configViewModel: ConfigViewModel,
        navHostController: NavHostController,
        onImportClick: () -> Unit,
        onExportClick: () -> Unit,
        isDebug: Boolean
    ) {
        val title by topBarViewModel.title.collectAsStateWithLifecycle()
        val currentBackStack by navHostController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentRoute = NavigationRoute.getCurrentNavigationRoute(currentDestination?.route)
        val context = LocalContext.current
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

        TopBar(
            title = title + if (isDebug) " ${packageInfo.versionName}" else "",
            onBackClick = {},
            actions = {
                GetTopBarMenuAction(
                    route = currentRoute,
                    onClickImport = onImportClick,
                    onClickExport = onExportClick,
                    topBarViewModel = topBarViewModel,
                    configViewModel = configViewModel
                )
            },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(
        title: String,
        onBackClick: () -> Unit,
        actions: @Composable (RowScope.() -> Unit) = {}
    ) {
        TopAppBar(
            title = { Text(text = title, fontSize = 20.sp) },
            navigationIcon = {},
            actions = actions
        )
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Composable
    private fun GetTopBarMenuAction(
        route: NavigationRoute,
        onClickImport: () -> Unit,
        onClickExport: () -> Unit,
        topBarViewModel: TopBarViewModel,
        configViewModel: ConfigViewModel,
    ) {
        var showDialog by remember { mutableStateOf(false) }
        return when(route) {
            is NavigationRoute.Decks -> {
                MainDropdownMenu(
                    onClickSortBy = { showDialog = true },
                    onClickImport = {
                        topBarViewModel.setImportMode(ImportMode.ALL)
                        onClickImport()
                    },
                    onClickExport = onClickExport,
                )
                SortDialog(
                    scope = ConfigScope.DECKS,
                    optionList = listOf<String>(
                        SortMode.ID_ASC.asString(ConfigScope.DECKS),
                        SortMode.ID_DESC.asString(ConfigScope.DECKS),
                        SortMode.TEXT_ASC.asString(ConfigScope.DECKS),
                        SortMode.TEXT_DESC.asString(ConfigScope.DECKS),
                        SortMode.LENGTH_ASC.asString(ConfigScope.DECKS),
                        SortMode.LENGTH_DESC.asString(ConfigScope.DECKS)
                    ),
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    configViewModel = configViewModel
                )
            }
            is NavigationRoute.DeckCards -> {
                DeckCardsDropdownMenu(
                    onClickSortBy = { showDialog = true },
                    onClickImportCards = {
                        topBarViewModel.setImportMode(ImportMode.CARDS)
                        onClickImport()
                    }
                )
                SortDialog(
                    scope = ConfigScope.CARDS,
                    optionList = listOf<String>(
                        SortMode.ID_ASC.asString(ConfigScope.CARDS),
                        SortMode.ID_DESC.asString(ConfigScope.CARDS),
                        SortMode.TEXT_ASC.asString(ConfigScope.CARDS),
                        SortMode.TEXT_DESC.asString(ConfigScope.CARDS),
                        SortMode.LENGTH_ASC.asString(ConfigScope.CARDS),
                        SortMode.LENGTH_DESC.asString(ConfigScope.CARDS)
                    ),
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    configViewModel = configViewModel
                )
            }
            else -> {  }
        }
    }
}
