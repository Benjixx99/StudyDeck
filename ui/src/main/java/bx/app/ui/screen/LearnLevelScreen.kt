package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import bx.app.presentation.viewmodel.CardInLevelViewModel
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.DialogHost
import bx.app.ui.composable.listmanager.LevelListManager
import bx.app.ui.composable.listmanager.LevelListManager.LevelListType
import bx.app.ui.data.InformationDialog

/**
 * This screen displays the list of levels that contains cards to learn
 */
@Composable
internal fun LearnLevelScreen(
    context: Context,
    levelViewModel: LevelViewModel,
    cardInLevelViewModel: CardInLevelViewModel,
    topBarViewModel: TopBarViewModel,
    onClickLearn: (id: Long) -> Unit
) {
    topBarViewModel.setTitle("Level system learning")
    val levels by levelViewModel.levels.collectAsState()
    val cardsCountByLevelId by cardInLevelViewModel.cardsCountByLevelId.collectAsState()
    val learnableCardsCountByLevelId by cardInLevelViewModel.learnableCardsCountByLevelId.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    levels.forEach {
        cardInLevelViewModel.countCardsByLevelId(it.id)
        cardInLevelViewModel.countLearnableCardsByLevelId(it.id)
    }

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val levelListManager = LevelListManager(
            items = levels,
            context = context,
            modifier = Modifier,
            searchText = "",
            onClick = {
                when {
                    (cardsCountByLevelId[it] == 0 || learnableCardsCountByLevelId[it] == 0) -> showDialog = true
                    else -> onClickLearn(it)
                }
            },
            type = LevelListType.Learn,
            cardsCountByLevelId = cardsCountByLevelId,
            learnableCardsCountByLevelId = learnableCardsCountByLevelId
        )
        levelListManager.List()
    }

    DialogHost(
        InformationDialog(
            isVisible = showDialog,
            message = "No cards to learn at this level right now!",
            onConfirm = { showDialog = false }
        )
    )
}