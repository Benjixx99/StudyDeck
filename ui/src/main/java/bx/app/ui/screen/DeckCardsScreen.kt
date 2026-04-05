package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
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
    topBarViewModel: TopBarViewModel,
    hideNavigationBarViewModel: HideNavigationBarViewModel,
    onClickCreateNewCard: () -> Unit = {},
    onClickCard: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Cards")
    var searchText by rememberSaveable { mutableStateOf("") }
    val cards by cardViewModel.cards.collectAsStateWithLifecycle()
    val selectedIds = remember { mutableStateSetOf<Long>() }

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        SearchBar(
            searchText = searchText,
            onChangeSearchText = { searchText = it }
        )
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