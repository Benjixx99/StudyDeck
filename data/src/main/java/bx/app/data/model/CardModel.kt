package bx.app.data.model

import bx.app.data.enums.CardSideType
import bx.app.data.local.entity.CardEntity
import kotlinx.serialization.Serializable

@Serializable
data class CardModel(
    override val id: Long = 0,
    val frontSideType: CardSideType = CardSideType.TEXT,
    val frontSideId: Long = 0,
    val backSideType: CardSideType = CardSideType.TEXT,
    val backSideId: Long = 0,
    val deckId: Long = 0,
    val frontText: String = "",
    val backText: String = "",
    val frontPath: String = "",
    val backPath: String = "",
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