package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ButtonInCorner
import bx.app.ui.composable.DeleteSelectionBar
import bx.app.ui.composable.SearchBar
import bx.app.ui.composable.listmanager.LevelListManager
import bx.app.ui.composable.listmanager.LevelListManager.LevelListType

/**
 * This screen displays a list of all levels that a deck contains
 */
@Composable
internal fun DeckLevelsScreen(
    context: Context,
    levelViewModel: LevelViewModel,
    topBarViewModel: TopBarViewModel,
    hideNavigationBarViewModel: HideNavigationBarViewModel,
    onClickCreateNewLevel: () -> Unit = {},
    onClickLevel: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Levels")
    val levels by levelViewModel.levels.collectAsState()
    val selectedIds = remember { mutableStateSetOf<Long>() }

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val searchText = SearchBar()
        val levelListManager = LevelListManager(
            items = levels,
            context = context,
            modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 40.dp),
            searchText = searchText,
            onClick = onClickLevel,
            onSelect = {
                if (!selectedIds.add(it)) selectedIds.remove(it)
                hideNavigationBarViewModel.setHide(selectedIds.isNotEmpty())
            },
            selectedIds = selectedIds,
            type = LevelListType.Edit
        )
        levelListManager.List()
    }
    if (selectedIds.isEmpty()) {
        ButtonInCorner(onClickCreateNewLevel)
    }
    else {
        DeleteSelectionBar(
            selectedIds = selectedIds,
            deleteAction = {
                levelViewModel.deleteLevelById(it)
                hideNavigationBarViewModel.setHide(false)
            }
        )
    }
}