package bx.app.ui

import androidx.navigation.NavHostController
import bx.app.ui.data.ConfirmationDialog
import bx.app.ui.data.DialogModel
import bx.app.ui.data.InformationDialog
import bx.app.ui.data.LearningMode
import bx.app.ui.data.LearningState

internal object LearningPhaseFactory {
    fun createDialogModel(
        learningMode: LearningMode,
        learningState: LearningState,
        knownCounter: Int,
        notKnownCounter: Int,
        navHostController: NavHostController,
        onClose: () -> Unit,
        onAnotherRound: () -> Unit = {}
    ): DialogModel {
        val isVisible = (learningState.completed() || learningState.cancelled())
        val message = "Known: $knownCounter\nNot known: $notKnownCounter\n\n" + when {
            learningMode.isRandom() && learningState.completed() -> "Another round?"
            learningMode.isLevelBased() && learningState.completed() -> "Learned all cards of this level"
            learningState.cancelled() -> "Cancel?"
            else -> ""
        }
        val onConfirm = {
            when {
                learningMode.isRandom() && learningState.completed() -> onAnotherRound()
                else -> navHostController.popBackStack()
            }
            onClose()
        }
        val onDismiss = {
            if (learningState.completed()) {
                navHostController.popBackStack()
            }
            onClose()
        }
        return when {
            learningMode.isRandom() || (learningMode.isLevelBased() && learningState.cancelled()) -> {
                ConfirmationDialog(
                    isVisible = isVisible,
                    message = message,
                    onConfirm = onConfirm,
                    onDismiss = onDismiss
                )
            }
            else -> {
                InformationDialog(
                    isVisible = isVisible,
                    message = message,
                    onConfirm = onConfirm
                )
            }
        }
    }
}