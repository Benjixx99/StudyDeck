package bx.app.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import bx.app.core.hasValidId
import bx.app.core.isInsert
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
import bx.app.ui.screen.RandomLearningPhaseScreen
import bx.app.ui.screen.LevelScreen
import bx.app.data.enums.CardSide
import bx.app.data.repository.CardInLevelRepository
import bx.app.data.repository.CardWithSidesRepository
import bx.app.presentation.viewmodel.CardInLevelViewModel
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
    private val cardInLevelViewModel = CardInLevelViewModel(CardInLevelRepository(database))
    private val textSideViewModel = TextSideViewModel(TextSideRepository(database))
    private val audioSideViewModel = AudioSideViewModel(AudioSideRepository(database))
    private val cardWithSidesViewModel = CardWithSidesViewModel(
        CardWithSidesRepository(database), cardViewModel, textSideViewModel, audioSideViewModel
    )
    private var deckId = 0L
        set(id) {
            if (id.hasValidId()) {
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
        DatabaseMockData.cardInLevel.forEach { cardInLevelViewModel.insertCardInLevel(it) }
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
        if (id.isInsert()) deckViewModel.resetDeck()
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

        if (id.hasValidId()) {
            cardViewModel.getCardById(id)
        }
        else if (id.isInsert()) {
            cardViewModel.resetCard()
            textSideViewModel.resetTextSide()
            audioSideViewModel.resetAudioSide()
        }
        CardScreen(
            context = context,
            cardWithSidesViewModel = cardWithSidesViewModel,
            navHostController = navHostController,
            topBarViewModel = topBarViewModel,
            cardSide = cardSide,
            deleteCard = { cardId = it }
        )
        if (cardId.hasValidId()) {
            cardWithSidesViewModel.deleteCardById(cardId)
        }

        NavigationBarItems.SetCardId(cardViewModel)
    }

    @Composable
    fun Level(id: Long) {
        var levelId by remember { mutableLongStateOf(0L) }

        if (id.hasValidId()) {
            levelViewModel.getLevelById(id)
        }
        else if (id.isInsert()) {
            levelViewModel.resetLevel()
        }
        LevelScreen(
            levelViewModel = levelViewModel,
            navHostController = navHostController,
            topBarViewModel = topBarViewModel,
            isInsert = (id.isInsert()),
            deleteLevel = { levelId = it }
        )
        if (levelId.hasValidId()) {
            levelViewModel.deleteLevelById(levelId)
        }
    }

    @Composable
    fun RandomLearningPhase(id: Long) {
        val learnBothSides = deckViewModel.deck.collectAsState().value.learnBothSides
        val shuffledCards = cardViewModel.cards.collectAsState().value.shuffled().toMutableList()

        if (shuffledCards.isNotEmpty()) {
            cardViewModel.getCardById(shuffledCards.first().id)

            RandomLearningPhaseScreen(
                learnBothSides = learnBothSides,
                cardWithSidesViewModel = cardWithSidesViewModel,
                navHostController = navHostController,
                topBarViewModel = topBarViewModel,
                shuffledCards = shuffledCards
            )
        }
    }

    @Composable
    fun LearnLevel(
        onClickLearn: (id: Long) -> Unit
    ) {
        LearnLevelScreen(
            context = context,
            levelViewModel = levelViewModel,
            cardInLevelViewModel = cardInLevelViewModel,
            topBarViewModel = topBarViewModel,
            onClickLearn = onClickLearn
        )
    }
}