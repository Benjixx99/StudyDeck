package bx.app.ui.composable

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
import bx.app.data.enums.SortMode
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.navigation.data.NavigationRoute

object TopBarComponent {
    @Composable
    fun Manager(
        topBarViewModel: TopBarViewModel,
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
                    topBarViewModel = topBarViewModel
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

    @Composable
    private fun GetTopBarMenuAction(
        route: NavigationRoute,
        onClickImport: () -> Unit,
        onClickExport: () -> Unit,
        topBarViewModel: TopBarViewModel,
    ) {
        return when(route) {
            is NavigationRoute.Decks -> {
                MainDropdownMenu(
                    onClickImport = onClickImport,
                    onClickExport = onClickExport
                )
            }
            is NavigationRoute.DeckCards -> {
                var showDialog by remember { mutableStateOf(false) }
                val sortMode by topBarViewModel.cardsSortMode.collectAsStateWithLifecycle()
                val optionList = listOf<String>(
                    SortMode.ID_ASC.asString(),
                    SortMode.ID_DESC.asString(),
                    SortMode.TEXT_ASC.asString(),
                    SortMode.TEXT_DESC.asString(),
                    SortMode.LENGTH_ASC.asString(),
                    SortMode.LENGTH_DESC.asString()
                )
                DeckCardsDropdownMenu(
                    onClickSortBy = { showDialog = true }
                )
                if (!showDialog) return

                RadioButtonGroupDialog(
                    headerText = "Sort by",
                    selectedOption = sortMode.ordinal,
                    optionList = optionList,
                    onDismissRequest = { showDialog = false },
                    onSelectOption = {
                        id, text ->
                        showDialog = false
                        topBarViewModel.setCardsSortMode(SortMode.fromInt(id))
                    }
                )
            }
            else -> {  }
        }
    }
}
