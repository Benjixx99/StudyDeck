package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import bx.app.core.IdPolicy
import bx.app.data.model.LearnModel
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.R
import bx.app.ui.composable.DialogHost
import bx.app.ui.composable.listmanager.LearnListManager
import bx.app.ui.data.InformationDialog

/**
 * This screen displays the learn options that are available
 */
@Composable
internal fun DeckLearnScreen(
    context: Context,
    cardViewModel: CardViewModel,
    topBarViewModel: TopBarViewModel,
    onClickLearn: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Learning")

    val items = listOf<LearnModel>(
        LearnModel(
            IdPolicy.getRandomId(),
            stringResource(R.string.random_learn_name),
            stringResource(R.string.random_learn_description)
        ),
        LearnModel(
            IdPolicy.getLevelBasedId(),
            stringResource(R.string.level_learn_name),
            stringResource(R.string.level_learn_description)
        ),
    )

    val deckId by cardViewModel.deckId.collectAsState()
    val cardsCountByDeckId by cardViewModel.cardsCountByDeckId.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    cardViewModel.countCardsByDeckId(deckId)

    Column(
        ModifierManager.paddingMostTopModifier
    ) {
        val learnListManager = LearnListManager(
            context = context,
            items = items,
            modifier = Modifier,
            searchText = "",
            onLearnClick = {
                when {
                    (it == IdPolicy.getRandomId() && cardsCountByDeckId[deckId] == 0) -> showDialog = true
                    else -> onClickLearn(it)
                }
            }
        )
        learnListManager.List()
    }

    DialogHost(
        InformationDialog(
            isVisible = showDialog,
            message = "This deck contains no cards!",
            onConfirm = { showDialog = false }
        )
    )
}