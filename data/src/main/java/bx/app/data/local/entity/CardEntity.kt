package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import bx.app.data.model.CardModel

@Entity(
    tableName = "card",
    foreignKeys = [ForeignKey(
        entity = DeckEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("deck_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "front_side_type")
    val frontSideType: Int,
    @ColumnInfo(name = "front_side_id")
    val frontSideId: Long,
    @ColumnInfo(name = "back_side_type")
    val backSideType: Int,
    @ColumnInfo(name = "back_side_id")
    val backSideId: Long,
    @ColumnInfo(name = "deck_id")
    val deckId: Long,
) : BaseEntity() {
    override fun toModel(): CardModel {
        return CardModel(
            id = this.id,
            frontSideType = this.frontSideType,
            frontSideId = this.frontSideId,
            backSideType = this.backSideType,
            backSideId = this.backSideId,
            deckId = this.deckId
        )
    }
}