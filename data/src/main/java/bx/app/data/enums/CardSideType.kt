package bx.app.data.enums

enum class CardSideType {
    TEXT, AUDIO;

    fun isText(): Boolean = this == TEXT
    fun isAudio(): Boolean = this == AUDIO
}