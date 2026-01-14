package bx.app.ui.data

import androidx.navigation.NavHostController

internal class LearningPhaseDialogState(
    val isVisible: Boolean = false,
    val message: String = "",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {}
) {
    fun init(
        endDialog: LearningState,
        knownCounter: Int,
        notKnownCounter: Int,
        navHostController: NavHostController,
        onAnotherRound: () -> Unit,
        onClose: () -> Unit
    ): LearningPhaseDialogState {
        val isVisible = (endDialog == LearningState.COMPLETED || endDialog == LearningState.CANCELLED)
        val message = when (endDialog) {
            LearningState.COMPLETED -> "Known: $knownCounter\nNot known: $notKnownCounter\nAnother round?"
            LearningState.CANCELLED -> "Cancel?"
            else -> ""
        }
        val onConfirm = {
            if (endDialog == LearningState.COMPLETED) onAnotherRound() else navHostController.popBackStack()
            onClose()
        }
        val onDismiss = {
            if (endDialog == LearningState.COMPLETED) navHostController.popBackStack()
            onClose()
        }
        return LearningPhaseDialogState(isVisible, message, onConfirm, onDismiss)
    }
}
