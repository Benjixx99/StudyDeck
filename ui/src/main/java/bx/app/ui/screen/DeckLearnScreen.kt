package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import bx.app.data.mock.item.BaseItem
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.R
import bx.app.ui.composable.listmanager.LearnListManager
import bx.app.ui.data.LearnData

/**
 * This screen displays the learn options that are available
 */
@Composable
internal fun DeckLearnScreen(
    context: Context,
    topBarViewModel: TopBarViewModel,
    onClickLearn: (id: Int) -> Unit = {},
) {
    topBarViewModel.setTitle("Learning")

    val items = listOf<BaseItem>(
        BaseItem(
            LearnData.RANDOM_ID,
            stringResource(R.string.random_learn_name),
            stringResource(R.string.random_learn_description)
        ),
        BaseItem(
            LearnData.LEVEL_ID,
            stringResource(R.string.level_learn_name),
            stringResource(R.string.level_learn_description)
        ),
    )
    Column(
        ModifierManager.paddingMostTopModifier
    ) {
        val learnListManager = LearnListManager(
            context = context,
            items = items,
            modifier = Modifier,
            searchText = "",
            onLearnClick = onClickLearn
        )
        learnListManager.List()
    }
}