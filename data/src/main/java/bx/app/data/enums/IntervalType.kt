package bx.app.data.enums

enum class IntervalType {
    WEEK, MONTH, YEAR;

    fun asString(): String = when (this) {
        WEEK -> "Week"
        MONTH -> "Month"
        YEAR -> "Year"
    }
}