package bx.app.data.enums

enum class IntervalType(val days: Int) {
    DAY(1), WEEK(7), MONTH(30), YEAR(365);

    fun asString(): String = when (this) {
        DAY -> "Day"
        WEEK -> "Week"
        MONTH -> "Month"
        YEAR -> "Year"
    }
}