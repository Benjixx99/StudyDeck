package bx.app.data.enums

enum class SortMode() {
    ID_ASC, ID_DESC, TEXT_ASC, TEXT_DESC, LENGTH_ASC, LENGTH_DESC;

    fun asString(scope: ConfigScope): String {
        val text =
            if (scope == ConfigScope.CARDS) "Text"
            else if (scope == ConfigScope.DECKS) "Name"
            else ""
        return when (this) {
            TEXT_ASC -> "$text (A - Z)"
            TEXT_DESC  -> "$text (Z - A)"
            ID_ASC -> "Date added (new to old)"
            ID_DESC -> "Date added (old to new)"
            LENGTH_ASC -> "$text length (long to short)"
            LENGTH_DESC -> "$text length (short to long)"
        }
    }

    companion object {
        fun fromInt(value: Int): SortMode =
            SortMode.entries.firstOrNull { it.ordinal == value }
                ?: throw IllegalArgumentException("Unknown SortMode value: $value")

        fun fromString(value: String): SortMode =
            try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Unknown SortMode value: $value")
            }
    }
}