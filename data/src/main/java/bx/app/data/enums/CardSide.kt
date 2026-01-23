package bx.app.data.enums

import bx.app.data.local.entity.CardEntity

enum class CardSide {
    FRONT, BACK;

    fun isFront(): Boolean = this == FRONT

    internal fun setTextSide(card: CardEntity, id: Long): CardEntity = when (this) {
        FRONT -> card.copy(frontSideType = CardSideType.TEXT, frontSideId = id)
        BACK -> card.copy(backSideType = CardSideType.TEXT, backSideId = id)
    }

    internal fun setAudioSide(card: CardEntity, id: Long): CardEntity = when (this) {
        FRONT -> card.copy(frontSideType = CardSideType.AUDIO, frontSideId = id)
        BACK -> card.copy(backSideType = CardSideType.AUDIO, backSideId = id)
    }
}
