package bx.app.data.enums

enum class SortMode() {
    ID_ASC, ID_DESC, TEXT_ASC, TEXT_DESC, LENGTH_ASC, LENGTH_DESC;

    fun asString(): String = when (this) {
        TEXT_ASC -> "Text ascending"
        TEXT_DESC -> "Text descending"
        ID_ASC -> "Date added ascending"
        ID_DESC -> "Date added descending"
        LENGTH_ASC -> "Text length ascending"
        LENGTH_DESC -> "Text length descending"
    }

    companion object {
        fun fromInt(value: Int): SortMode =
            SortMode.entries.firstOrNull { it.ordinal == value }
                ?: throw IllegalArgumentException("Unknown SortMode value: $value")
    }
}