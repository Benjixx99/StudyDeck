package bx.app.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import bx.app.data.local.AppDatabase
import bx.app.data.local.DatabaseBuilder
import bx.app.data.local.mock.DatabaseMockData
import bx.app.data.repository.AudioSideRepository
import bx.app.data.repository.CardRepository
import bx.app.data.repository.DeckRepository
import bx.app.data.repository.LevelRepository
import bx.app.data.repository.TextSideRepository
import bx.app.presentation.viewmodel.AudioSideViewModel
import bx.app.presentation.viewmodel.CardViewModel
import bx.app.presentation.viewmodel.DeckViewModel
import bx.app.presentation.viewmodel.LevelViewModel
import bx.app.presentation.viewmodel.TextSideViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.navigation.data.DatabaseOperation
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
    private val textSideViewModel = TextSideViewModel(TextSideRepository(database))
    private val audioSideViewModel = AudioSideViewModel(AudioSideRepository(database))
    private var deckId: Long = 0

    init {
        context.deleteDatabase(DatabaseBuilder.DATABASE_NAME)
        DatabaseMockData.decks.forEach { deckViewModel.insertDeck(it) }
        DatabaseMockData.cards.forEach { cardViewModel.insertCard(it) }
        DatabaseMockData.levels.forEach { levelViewModel.insertLevel(it) }
        DatabaseMockData.textSide.forEach { textSideViewModel.insertTextSide(it) }
        DatabaseMockData.audioSide.forEach { audioSideViewModel.insertAudioSide(it) }
    }

    @Composable
    fun Decks(
        onClickCreateNewDeck: () -> Unit = {},
        onClickDeck: (id: Long) -> Unit = {},
    ) {
        DecksScreen(context, deckViewModel, topBarViewModel, onClickCreateNewDeck, onClickDeck)
    }

    @Composable
    fun DeckCards(
        id: Long,
        onClickCreateNewCard: () -> Unit = {},
        onClickCard: (id: Long) -> Unit = {},
    ) {
        if (id > 0) {
            deckId = id
            cardViewModel.setDeckId(id)
            deckViewModel.getDeckById(id)
        }
        DeckCardsScreen(context, cardViewModel, textSideViewModel, audioSideViewModel,
            topBarViewModel, onClickCreateNewCard, onClickCard)
    }

    @Composable
    fun DeckSettings(id: Long) {
        if (id == DatabaseOperation.INSERT) deckViewModel.resetDeck()
        DeckSettingsScreen(deckViewModel, topBarViewModel, context)
        DeckSettingsScreen(context, deckViewModel, topBarViewModel)
    }

    @Composable
    fun DeckLevels(
        onClickCreateNewLevel: () -> Unit = {},
        onClickLevel: (id: Long) -> Unit = {},
    ) {
        DeckLevelsScreen(context, levelViewModel, topBarViewModel, onClickCreateNewLevel, onClickLevel)
    }

    @Composable
    fun DeckLearn(
        onClickLearn: (id: Long) -> Unit = {},
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
        onClickLearn: (id: Long) -> Unit
    ) {
        LearnLevelScreen(context, levelViewModel, topBarViewModel, onClickLearn)
    }
}