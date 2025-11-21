package bx.app.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import bx.app.data.local.AppDatabase
import bx.app.data.local.DatabaseBuilder
import bx.app.data.local.mock.DatabaseMockData
import bx.app.data.repository.CardRepository
import bx.app.data.repository.DeckRepository
import bx.app.data.repository.LevelRepository
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.DeckViewModel
import bx.app.presentation.viewmodel.LevelViewModel
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
    private val database: AppDatabase = DatabaseBuilder.getInstance(context)
    private val deckViewModel = DeckViewModel(DeckRepository(database))
    private val cardViewModel = CardViewModel(CardRepository(database))
    private val levelViewModel = LevelViewModel(LevelRepository(database))

    init {
        context.deleteDatabase(DatabaseBuilder.DATABASE_NAME)
        DatabaseMockData.decks.forEach { deckViewModel.insertDeck(it) }
        DatabaseMockData.cards.forEach { cardViewModel.insertCard(it) }
        DatabaseMockData.levels.forEach { levelViewModel.insertLevel(it) }
    }

    @Composable
    fun Decks(
        onClickCreateNewDeck: () -> Unit = {},
        onClickDeck: () -> Unit = {},
    ) {
        DecksScreen(context, deckViewModel, topBarViewModel, onClickCreateNewDeck, onClickDeck)
    }

    @Composable
    fun DeckCards(
        onClickCreateNewCard: () -> Unit = {},
        onClickCard: () -> Unit = {},
    ) {
        DeckCardsScreen(context, cardViewModel, topBarViewModel, onClickCreateNewCard, onClickCard)
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
        DeckLevelsScreen(context, levelViewModel, topBarViewModel, onClickCreateNewLevel, onClickLevel)
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
        LearnLevelScreen(context, levelViewModel, topBarViewModel, onClickLearn)
    }
}