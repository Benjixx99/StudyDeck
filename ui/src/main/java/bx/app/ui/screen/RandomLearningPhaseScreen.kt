package bx.app.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    val card by cardWithSidesViewModel.cardViewModel.card.collectAsStateWithLifecycle()
    var knownCounter by remember { mutableIntStateOf(0) }
    var notKnownCounter by remember { mutableIntStateOf(0) }
    var learningState by remember { mutableStateOf(LearningState.IN_PROGRESS) }
    var anotherRound by remember { mutableStateOf(false) }

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
            .cards.collectAsStateWithLifecycle().value.shuffled() as MutableList<CardModel>)
        cardWithSidesViewModel.cardViewModel.getCardById(shuffledCards.first().id)
        knownCounter = 0
        notKnownCounter = 0
    }

    LearningPhase(
        params = LearningPhaseParams.from(card),
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
        isActive = (learningState.inProgress()),
        learnBothSides = learnBothSides,
        learningMode = LearningMode.RANDOM
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