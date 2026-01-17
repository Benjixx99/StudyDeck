package bx.app.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import bx.app.data.model.CardModel
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.composable.DialogHost
import bx.app.ui.composable.LearningPhase
import bx.app.ui.data.LearningMode
import bx.app.ui.LearningPhaseFactory
import bx.app.ui.data.LearningPhaseParams
import bx.app.ui.data.LearningState

/**
 * This screen displays the learning phase
 */
@Composable
internal fun RandomLearningPhaseScreen(
    learnBothSides: Boolean,
    cardWithSidesViewModel: CardWithSidesViewModel,
    topBarViewModel: TopBarViewModel,
    navHostController: NavHostController,
    shuffledCards: MutableList<CardModel>
) {
    topBarViewModel.setTitle("Random learning phase")

    val textById by cardWithSidesViewModel.textSideViewModel.textById.collectAsState()
    val fileNameById by cardWithSidesViewModel.audioSideViewModel.fileNameById.collectAsState()
    val pathById by cardWithSidesViewModel.audioSideViewModel.pathById.collectAsState()
    val card by cardWithSidesViewModel.cardViewModel.card.collectAsState()
    var knownCounter by remember { mutableIntStateOf(0) }
    var notKnownCounter by remember { mutableIntStateOf(0) }
    var learningState by remember { mutableStateOf(LearningState.IN_PROGRESS) }
    var anotherRound by remember { mutableStateOf(false) }

    shuffledCards.forEach { cardWithSidesViewModel.getCardSideValues(it) }

    BackHandler { learningState = LearningState.CANCELLED }
    DialogHost(
        LearningPhaseFactory.createDialogModel(
            learningMode = LearningMode.RANDOM,
            learningState = learningState,
            knownCounter = knownCounter,
            notKnownCounter = notKnownCounter,
            navHostController = navHostController,
            onAnotherRound = { anotherRound = true },
            onClose = { learningState = LearningState.IN_PROGRESS }
        )
    )

    if (anotherRound) {
        anotherRound = false
        shuffledCards.addAll(0, cardWithSidesViewModel.cardViewModel
            .cards.collectAsState().value.shuffled() as MutableList<CardModel>)
        cardWithSidesViewModel.cardViewModel.getCardById(shuffledCards.first().id)
        knownCounter = 0
        notKnownCounter = 0
    }

    LearningPhase(
        params = LearningPhaseParams.from(
            textById = textById,
            fileNameById = fileNameById,
            pathById = pathById,
            card = card
        ),
        learnBothSides = learnBothSides,
        onNotKnown = {
            proceedToNextCard(
                shuffledCards = shuffledCards,
                increment = { ++notKnownCounter },
                setState = { learningState = it },
                loadNext = { cardWithSidesViewModel.cardViewModel.getCardById(it) }
            )
        },
        onKnown = {
            proceedToNextCard(
                shuffledCards = shuffledCards,
                increment = { ++knownCounter },
                setState = { learningState = it },
                loadNext = { cardWithSidesViewModel.cardViewModel.getCardById(it) }
            )
        },
        isActive = (learningState == LearningState.IN_PROGRESS)
    )
}

private fun proceedToNextCard(
    shuffledCards: MutableList<CardModel>,
    increment: () -> Unit,
    setState: (LearningState) -> Unit,
    loadNext: (Long) -> Unit
) {
    increment()
    if (shuffledCards.isNotEmpty()) shuffledCards.removeAt(0)
    if (shuffledCards.isEmpty()) {
        setState(LearningState.COMPLETED)
        return
    }
    loadNext(shuffledCards.first().id)
}