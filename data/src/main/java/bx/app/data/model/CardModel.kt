package bx.app.data.model

import bx.app.data.local.entity.CardEntity

data class CardModel(
    val id: Long = 0,
    val frontSideType: Int,
    val frontSideId: Long,
    val backSideType: Int,
    val backSideId: Long,
    val deckId: Long,
) : BaseModel() {
    override fun toEntity(): CardEntity {
        return CardEntity(
            id = this.id,
            frontSideType = this.frontSideType,
            frontSideId = this.frontSideId,
            backSideType = this.backSideType,
            backSideId = this.backSideId,
            deckId = this.deckId
        )
    }
}