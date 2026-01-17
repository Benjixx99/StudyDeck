package bx.app.ui.data

sealed interface DialogModel {
    val isVisible: Boolean
    val message: String
    val confirmText: String
    val onConfirm: () -> Unit
}

data class InformationDialog(
    override val isVisible: Boolean,
    override val message: String,
    override val confirmText: String = "Okay",
    override val onConfirm: () -> Unit,
) : DialogModel

data class ConfirmationDialog(
    override val isVisible: Boolean,
    override val message: String,
    override val confirmText: String = "Yes",
    override val onConfirm: () -> Unit,
    val dismissText: String = "No",
    val onDismiss: () -> Unit
) : DialogModel