package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.listmanager.LevelListManager
import bx.app.ui.composable.listmanager.LevelListManager.LevelListType

/**
 * This screen displays the list of levels that contains cards to learn
 */
@Composable
internal fun LearnLevelScreen(
    context: Context,
    levelViewModel: LevelViewModel,
    topBarViewModel: TopBarViewModel,
    onClickLearn: (id: Long) -> Unit
) {
    topBarViewModel.setTitle("Level system learning")
    val levels by levelViewModel.levels.collectAsState()

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val levelListManager = LevelListManager(
            items = levels,
            context = context,
            modifier = Modifier,
            searchText = "",
            onClick = onClickLearn,
            type = LevelListType.Learn
        )
        levelListManager.List()
    }
}