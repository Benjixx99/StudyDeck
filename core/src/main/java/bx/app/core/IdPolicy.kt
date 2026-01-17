package bx.app.core

object IdPolicy {
    internal const val MIN_VALID_ID = 1L
    internal const val INSERT = -1L
    internal const val RANDOM_ID = -10L
    internal const val LEVEL_BASED_ID = -11L

    fun getInsertId(): Long = INSERT
    fun getRandomId(): Long = RANDOM_ID
    fun getLevelBasedId(): Long = LEVEL_BASED_ID
}