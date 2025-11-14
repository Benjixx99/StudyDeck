package bx.app.data.model

import bx.app.data.enums.CardSide
import bx.app.data.local.entity.TextSideEntity

data class TextSideModel(
    val id: Long = 0,
    val text: String,
    val side: CardSide,
    val cardId: Long,
) : BaseModel() {
    override fun toEntity(): TextSideEntity {
        return TextSideEntity(
            id = this.id,
            text = this.text,
            side = this.side,
            cardId = this.cardId
        )
    }
}