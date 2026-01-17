package bx.app.ui.data

internal enum class LearningState {
    IN_PROGRESS, CANCELLED, COMPLETED;

    fun inProgress(): Boolean = this == IN_PROGRESS
    fun cancelled(): Boolean = this == CANCELLED
    fun completed(): Boolean = this == COMPLETED
}