package bx.app.ui.data

internal enum class LearningMode {
    RANDOM, LEVEL_BASED;

    fun isRandom(): Boolean = this == RANDOM
    fun isLevelBased(): Boolean = this == LEVEL_BASED
}