package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideConverter
import bx.app.data.model.TextSideModel

@Entity(
    tableName = "text_side",
    foreignKeys = [ForeignKey(
        entity = CardEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("card_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class TextSideEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    @TypeConverters(CardSideConverter::class)
    val side: CardSide,
    @ColumnInfo(name = "card_id")
    val cardId: Long,
) : BaseEntity() {
    override fun toModel(): TextSideModel {
        return TextSideModel(
            id = this.id,
            text = this.text,
            side = this.side,
            cardId = this.cardId
        )
    }
}