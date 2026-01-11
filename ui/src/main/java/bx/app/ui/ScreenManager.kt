package bx.app.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
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
import bx.app.ui.screen.CardScreen
import bx.app.ui.screen.DeckCardsScreen
import bx.app.ui.screen.DeckLearnScreen
import bx.app.ui.screen.DeckLevelsScreen
import bx.app.ui.screen.DeckSettingsScreen
import bx.app.ui.screen.DecksScreen
import bx.app.ui.screen.LearnLevelScreen
import bx.app.ui.screen.LearnPhaseScreen
import bx.app.ui.screen.LevelScreen
import bx.app.data.enums.CardSide
import bx.app.data.repository.CardWithSidesRepository
import bx.app.presentation.data.IdValidator
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.ui.navigation.data.NavigationBarItems

/**
 * Manage all the screens for the app
 */
class ScreenManager(
    private val context: Context,
    private val navHostController: NavHostController,
    private val topBarViewModel: TopBarViewModel,
    private val hideNavigationBarViewModel: HideNavigationBarViewModel
) {
    private val database: AppDatabase = DatabaseBuilder.getInstance(context)
    private val deckViewModel = DeckViewModel(DeckRepository(database))
    private val cardViewModel = CardViewModel(CardRepository(database))
    private val levelViewModel = LevelViewModel(LevelRepository(database))
    private val textSideViewModel = TextSideViewModel(TextSideRepository(database))
    private val audioSideViewModel = AudioSideViewModel(AudioSideRepository(database))
    private val cardWithSidesViewModel = CardWithSidesViewModel(
        CardWithSidesRepository(database), cardViewModel, textSideViewModel, audioSideViewModel
    )
    private var deckId = 0L
        set(id) {
            if (id >= IdValidator.MIN_VALID_ID) {
                field = id
                cardViewModel.setDeckId(id)
                levelViewModel.setDeckId(id)
                deckViewModel.getDeckById(id)
            }
        }

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
        DecksScreen(
            context = context,
            deckViewModel = deckViewModel,
            cardWithSidesViewModel = cardWithSidesViewModel,
            levelViewModel = levelViewModel,
            topBarViewModel = topBarViewModel,
            onClickCreateNewDeck = onClickCreateNewDeck,
            onClickDeck = onClickDeck
        )
    }

    @Composable
    fun DeckCards(
        id: Long,
        onClickCreateNewCard: () -> Unit = {},
        onClickCard: (id: Long) -> Unit = {},
    ) {
        deckId = id
        DeckCardsScreen(
            context = context,
            cardWithSidesViewModel = cardWithSidesViewModel,
            cardViewModel = cardViewModel,
            textSideViewModel = textSideViewModel,
            audioSideViewModel = audioSideViewModel,
            topBarViewModel = topBarViewModel,
            hideNavigationBarViewModel = hideNavigationBarViewModel,
            onClickCreateNewCard = onClickCreateNewCard,
            onClickCard = onClickCard
        )
        NavigationBarItems.SetDeckId(deckViewModel)
    }

    @Composable
    fun DeckSettings(id: Long) {
        if (id == IdValidator.INSERT) deckViewModel.resetDeck()
        DeckSettingsScreen(
            context = context,
            deckViewModel = deckViewModel,
            topBarViewModel = topBarViewModel,
            hideNavigationBarViewModel = hideNavigationBarViewModel
        )
        NavigationBarItems.SetDeckId(deckViewModel)
    }

    @Composable
    fun DeckLevels(
        id: Long,
        onClickCreateNewLevel: () -> Unit = {},
        onClickLevel: (id: Long) -> Unit = {},
    ) {
        deckId = id
        DeckLevelsScreen(
            context = context,
            levelViewModel = levelViewModel,
            topBarViewModel = topBarViewModel,
            hideNavigationBarViewModel = hideNavigationBarViewModel,
            onClickCreateNewLevel = onClickCreateNewLevel,
            onClickLevel = onClickLevel
        )
    }

    @Composable
    fun DeckLearn(
        id: Long,
        onClickLearn: (id: Long) -> Unit = {},
    ) {
        deckId = id
        DeckLearnScreen(context, topBarViewModel, onClickLearn)
    }

    @Composable
    fun Card(id: Long, cardSide: CardSide) {
        var cardId by remember { mutableLongStateOf(0L) }

        if (id >= IdValidator.MIN_VALID_ID) {
            cardViewModel.getCardById(id)
        }
        else if (id == IdValidator.INSERT) {
            cardViewModel.resetCard()
            textSideViewModel.resetTextSide()
            audioSideViewModel.resetAudioSide()
        }
        CardScreen(
            context = context,
            cardWithSidesViewModel = cardWithSidesViewModel,
            topBarViewModel = topBarViewModel,
            cardSide = cardSide,
            navHostController = navHostController,
            deleteCard = { cardId = it }
        )
        if (cardId >= IdValidator.MIN_VALID_ID) cardWithSidesViewModel.deleteCardById(cardId)

        NavigationBarItems.SetCardId(cardViewModel)
    }

    @Composable
    fun Level(id: Long) {
        var levelId by remember { mutableLongStateOf(0L) }

        if (id >= IdValidator.MIN_VALID_ID) {
            levelViewModel.getLevelById(id)
        }
        else if (id == IdValidator.INSERT) {
            levelViewModel.resetLevel()
        }
        LevelScreen(
            levelViewModel = levelViewModel,
            navHostController = navHostController,
            topBarViewModel = topBarViewModel,
            isInsert = (id == IdValidator.INSERT),
            deleteLevel = { levelId = it }
        )
        if (levelId >= IdValidator.MIN_VALID_ID) levelViewModel.deleteLevelById(levelId)
    }

    @Composable
    fun LearnPhase() {
        LearnPhaseScreen(topBarViewModel)
    }

    @Composable
    fun LearnLevel(
        onClickLearn: (id: Long) -> Unit
    ) {
        LearnLevelScreen(
            context = context,
            levelViewModel = levelViewModel,
            topBarViewModel = topBarViewModel,
            onClickLearn = onClickLearn
        )
    }
}