package bx.app.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.ButtonInCorner
import bx.app.ui.composable.SearchBar
import bx.app.ui.composable.listmanager.CardListManager

/**
 * This screen displays a list of all cards that a deck contains
 */
@Composable
internal fun DeckCardsScreen(
    context: Context,
    cardViewModel: CardViewModel,
    topBarViewModel: TopBarViewModel,
    onClickCreateNewCard: () -> Unit = {},
    onClickCard: () -> Unit = {},
) {
    topBarViewModel.setTitle("Cards")
    val cards by cardViewModel.cards.collectAsState()

    Column(
        modifier = ModifierManager.paddingMostTopModifier
    ) {
        val searchText = SearchBar()
        val cardListManager = CardListManager(
            items = cards,
            context = context,
            modifier = ModifierManager.paddingTopModifier.fillMaxSize().padding(bottom = 40.dp),
            searchText = searchText,
            onClick = onClickCreateNewCard,
        )
        cardListManager.List()
    }
    ButtonInCorner(onClickCreateNewCard)
}