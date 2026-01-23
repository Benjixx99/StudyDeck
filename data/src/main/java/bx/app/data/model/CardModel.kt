package bx.app.data.model

import bx.app.data.enums.CardSideType
import bx.app.data.local.entity.CardEntity
import kotlinx.serialization.Serializable

@Serializable
data class CardModel(
    override val id: Long = 0,
    val frontSideType: CardSideType,
    val frontSideId: Long,
    val backSideType: CardSideType,
    val backSideId: Long,
    val deckId: Long,
) : IdentifiedModel() {
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