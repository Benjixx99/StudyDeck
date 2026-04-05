package bx.app.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import bx.app.data.local.entity.BaseEntity
import bx.app.data.local.entity.CardEntity
import bx.app.data.model.CardModel

internal data class CardWithSideContentDto(
    @Embedded
    val card: CardEntity,
    @ColumnInfo(name = "front_text")
    val frontText: String?,
    @ColumnInfo(name = "back_text")
    val backText: String?,
    @ColumnInfo(name = "front_path")
    val frontPath: String?,
    @ColumnInfo(name = "back_path")
    val backPath: String?,
    @ColumnInfo(name = "front_text_length")
    val frontLength: Int
) : BaseEntity() {
    override fun toModel(): CardModel {
        return CardModel(
            id = this.card.id,
            frontSideType = this.card.frontSideType,
            frontSideId = this.card.frontSideId,
            backSideType = this.card.backSideType,
            backSideId = this.card.backSideId,
            deckId = this.card.deckId,
            frontText = this.frontText.toString(),
            backText = this.backText.toString(),
            frontPath = this.frontPath.toString(),
            backPath = this.backPath.toString(),
        )
    }
}