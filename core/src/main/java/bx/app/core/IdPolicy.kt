package bx.app.core

object IdPolicy {
    internal const val MIN_VALID_ID = 1L
    internal const val INSERT = -1L

    fun getInsertId(): Long = INSERT
}