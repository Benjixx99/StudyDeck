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
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.DeckViewModel
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ButtonInCorner
import bx.app.ui.composable.DeleteSelectionBar
import bx.app.ui.composable.SearchBar
import bx.app.ui.composable.listmanager.DeckListManager

/**
 * This screen displays a list of all decks that the user created
 */
@Composable
internal fun DecksScreen(
    context: Context,
    deckViewModel: DeckViewModel,
    cardWithSidesViewModel: CardWithSidesViewModel,
    levelViewModel: LevelViewModel,
    topBarViewModel: TopBarViewModel,
    onClickCreateNewDeck: () -> Unit = {},
    onClickDeck: (id: Long) -> Unit = {},
) {
    topBarViewModel.setTitle("Decks")
    val decks by deckViewModel.decks.collectAsState()
    val selectedIds = remember { mutableStateSetOf<Long>() }
    val cardsCountByDeckId by cardWithSidesViewModel.cardViewModel.cardsCountByDeckId.collectAsState()

    decks.forEach {
        cardWithSidesViewModel.cardViewModel.countCardsByDeckId(it.id)
    }

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val searchText = SearchBar()
        val deckListManager = DeckListManager(
            items = decks,
            context = context,
            modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 10.dp),
            searchText = searchText,
            onClick = onClickDeck,
            onSelect = { if (!selectedIds.add(it)) selectedIds.remove(it) },
            selectedIds = selectedIds,
            cardsCountByDeckId = cardsCountByDeckId
        )
        deckListManager.List()
    }
    if (selectedIds.isEmpty()) {
        ButtonInCorner(onClickCreateNewDeck)
    }
    else {
        DeleteSelectionBar(
            selectedIds = selectedIds,
            deleteAction = {
                cardWithSidesViewModel.deleteCardsByDeckId(it)
                levelViewModel.deleteLevelsByDeckId(it)
                deckViewModel.deleteDeckByIe(it)
            }
        )
    }
}