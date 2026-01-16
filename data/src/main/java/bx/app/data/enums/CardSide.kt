package bx.app.data.enums

enum class CardSide {
    FRONT, BACK;

    fun isFront(): Boolean = this == FRONT
}
