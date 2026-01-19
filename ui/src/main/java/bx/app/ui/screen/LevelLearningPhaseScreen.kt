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
import bx.app.data.enums.CardFailing
import bx.app.data.model.CardModel
import bx.app.presentation.viewmodel.CardInLevelViewModel
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.composable.DialogHost
import bx.app.ui.composable.LearningPhase
import bx.app.ui.data.LearningMode
import bx.app.ui.LearningPhaseFactory
import bx.app.ui.data.LearningPhaseParams
import bx.app.ui.data.LearningState
import kotlin.collections.forEach


/**
 * This screen displays the level based learning phase
 */
@Composable
internal fun LevelLearningPhaseScreen(
    levelId: Long,
    onFailing: CardFailing,
    learnBothSides: Boolean,
    cardWithSidesViewModel: CardWithSidesViewModel,
    cardInLevelViewModel: CardInLevelViewModel,
    topBarViewModel: TopBarViewModel,
    navHostController: NavHostController,
    shuffledCards: MutableList<CardModel>
) {
    topBarViewModel.setTitle("Level learning phase")

    val textById by cardWithSidesViewModel.textSideViewModel.textById.collectAsState()
    val fileNameById by cardWithSidesViewModel.audioSideViewModel.fileNameById.collectAsState()
    val pathById by cardWithSidesViewModel.audioSideViewModel.pathById.collectAsState()
    val card by cardWithSidesViewModel.cardViewModel.card.collectAsState()

    cardInLevelViewModel.getLastTimeLearnedFrontByCardId(card.id)
    val lastTimeLearnedFront by cardInLevelViewModel.lastTimeLearnedFront.collectAsState()

    var knownCounter by remember { mutableIntStateOf(0) }
    var notKnownCounter by remember { mutableIntStateOf(0) }
    var learningState by remember { mutableStateOf(LearningState.IN_PROGRESS) }

    shuffledCards.forEach { cardWithSidesViewModel.getCardSideValues(it) }

    BackHandler { learningState = LearningState.CANCELLED }
    DialogHost(
        LearningPhaseFactory.createDialogModel(
            learningMode = LearningMode.LEVEL_BASED,
            learningState = learningState,
            knownCounter = knownCounter,
            notKnownCounter = notKnownCounter,
            navHostController = navHostController,
            onClose = { learningState = LearningState.IN_PROGRESS }
        )
    )

    LearningPhase(
        params = LearningPhaseParams.from(
            textById = textById,
            fileNameById = fileNameById,
            pathById = pathById,
            card = card,
            lastTimeLearnedFront = lastTimeLearnedFront
        ),
        onNotKnown = {
            cardInLevelViewModel.updateCardInLevel(
                levelId = levelId,
                cardId = shuffledCards.first().id,
                learnBothSides = learnBothSides,
                onFailing = onFailing,
            )
            proceedToNextCard(
                shuffledCards = shuffledCards,
                increment = { ++notKnownCounter },
                setState = { learningState = it },
                loadNext = { cardWithSidesViewModel.cardViewModel.getCardById(it) }
            )
        },
        onKnown = {
            cardInLevelViewModel.updateCardInLevel(
                levelId = levelId,
                cardId = shuffledCards.first().id,
                learnBothSides = learnBothSides,
            )
            proceedToNextCard(
                shuffledCards = shuffledCards,
                increment = { ++knownCounter },
                setState = { learningState = it },
                loadNext = { cardWithSidesViewModel.cardViewModel.getCardById(it) }
            )
        },
        isActive = (learningState.inProgress()),
        learnBothSides = learnBothSides,
        learningMode = LearningMode.LEVEL_BASED,
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