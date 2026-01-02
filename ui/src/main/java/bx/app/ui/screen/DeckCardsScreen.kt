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
import bx.app.data.enums.CardSideType
import bx.app.presentation.viewmodel.AudioSideViewModel
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.presentation.viewmodel.TextSideViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ButtonInCorner
import bx.app.ui.composable.DeleteSelectionBar
import bx.app.ui.composable.SearchBar
import bx.app.ui.composable.listmanager.CardListManager

/**
 * This screen displays a list of all cards that a deck contains
 */
@Composable
internal fun DeckCardsScreen(
    context: Context,
    cardWithSidesViewModel: CardWithSidesViewModel,
    cardViewModel: CardViewModel,
    textSideViewModel: TextSideViewModel,
    audioSideViewModel: AudioSideViewModel,
    topBarViewModel: TopBarViewModel,
    hideNavigationBarViewModel: HideNavigationBarViewModel,
    onClickCreateNewCard: () -> Unit = {},
    onClickCard: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Cards")
    val cards by cardViewModel.cards.collectAsState()
    val textById by textSideViewModel.textById.collectAsState()
    val fileNameById by audioSideViewModel.fileNameById.collectAsState()
    val selectedIds = remember { mutableStateSetOf<Long>() }

    cards.forEach {
        if (it.frontSideType == CardSideType.TEXT) { textSideViewModel.getTextById(it.frontSideId) }
        if (it.frontSideType == CardSideType.AUDIO) { audioSideViewModel.getFileNameById(it.frontSideId) }
        if (it.backSideType == CardSideType.TEXT) { textSideViewModel.getTextById(it.backSideId) }
        if (it.backSideType == CardSideType.AUDIO) { audioSideViewModel.getFileNameById(it.backSideId) }
    }

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val searchText = SearchBar()
        val cardListManager = CardListManager(
            items = cards,
            context = context,
            modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 40.dp),
            searchText = searchText,
            onClick = onClickCard,
            onSelect = {
                if (!selectedIds.add(it)) selectedIds.remove(it)
                hideNavigationBarViewModel.setHide(selectedIds.isNotEmpty())
            },
            selectedIds = selectedIds,
            textById = textById,
            fileNameById = fileNameById,
        )
        cardListManager.List()
    }
    if (selectedIds.isEmpty()) {
        ButtonInCorner(onClickCreateNewCard)
    }
    else {
        DeleteSelectionBar(
            selectedIds = selectedIds,
            deleteAction = {
                cardWithSidesViewModel.deleteCardById(it)
                hideNavigationBarViewModel.setHide(false)
            }
        )
    }
}