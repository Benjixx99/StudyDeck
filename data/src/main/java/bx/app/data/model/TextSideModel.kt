package bx.app.data.model

import bx.app.data.enums.CardType
import bx.app.data.local.entity.TextSideEntity

data class TextSideModel(
    val id: Long = 0,
    val text: String,
    val side: CardType,
    val cardId: Long,
) : BaseModel() {
    override fun toEntity(): TextSideEntity {
        return TextSideEntity(
            id = this.id,
            text = this.text,
            side = if (this.side == CardType.Text) 0 else 1,
            cardId = this.cardId
        )
    }
}