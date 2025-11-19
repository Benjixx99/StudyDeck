package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bx.app.data.mock.MockData
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
    topBarViewModel: TopBarViewModel,
    onClickLearn: () -> Unit
) {
    topBarViewModel.setTitle("Level system learning")

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val levelListManager = LevelListManager(
            items = MockData.levelItems,
            context = context,
            modifier = Modifier,
            searchText = "",
            onClick = onClickLearn,
            type = LevelListType.Learn
        )
        levelListManager.List()
    }
}