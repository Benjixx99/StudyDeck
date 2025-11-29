package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ButtonInCorner
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
    onClickCreateNewLevel: () -> Unit = {},
    onClickLevel: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Levels")
    val levels by levelViewModel.levels.collectAsState()

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
            type = LevelListType.Edit
        )
        levelListManager.List()
    }
    ButtonInCorner(onClickCreateNewLevel)
}