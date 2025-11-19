package bx.app.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.screen.CardScreen
import bx.app.ui.screen.DeckCardsScreen
import bx.app.ui.screen.DeckLearnScreen
import bx.app.ui.screen.DeckLevelsScreen
import bx.app.ui.screen.DeckSettingsScreen
import bx.app.ui.screen.DecksScreen
import bx.app.ui.screen.LearnLevelScreen
import bx.app.ui.screen.LearnPhaseScreen
import bx.app.ui.screen.LevelScreen

/**
 * Manage all the screens for the app
 */
class ScreenManager(private var context: Context, private val topBarViewModel: TopBarViewModel) {

    @Composable
    fun Decks(
        onClickCreateNewDeck: () -> Unit = {},
        onClickDeck: () -> Unit = {},
    ) {
        DecksScreen(context, topBarViewModel, onClickCreateNewDeck, onClickDeck)
    }

    @Composable
    fun DeckCards(
        onClickCreateNewCard: () -> Unit = {},
        onClickCard: () -> Unit = {},
    ) {
        DeckCardsScreen(context, topBarViewModel, onClickCreateNewCard, onClickCard)
    }

    @Composable
    fun DeckSettings() {
        DeckSettingsScreen(topBarViewModel)
    }

    @Composable
    fun DeckLevels(
        onClickCreateNewLevel: () -> Unit = {},
        onClickLevel: () -> Unit = {},
    ) {
        DeckLevelsScreen(context, topBarViewModel, onClickCreateNewLevel, onClickLevel)
    }

    @Composable
    fun DeckLearn(
        onClickLearn: (id: Int) -> Unit = {},
    ) {
        DeckLearnScreen(context, topBarViewModel, onClickLearn)
    }

    @Composable
    fun Card() {
        CardScreen(context, topBarViewModel)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Level() {
        LevelScreen(topBarViewModel)
    }

    @Composable
    // TODO: Hide the navigation bar on this screen!
    fun LearnPhase() {
        LearnPhaseScreen(topBarViewModel)
    }

    @Composable
    fun LearnLevel(
        onClickLearn: () -> Unit
    ) {
        LearnLevelScreen(context, topBarViewModel, onClickLearn)
    }
}