package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import bx.app.data.enums.CardType
import bx.app.data.model.AudioSideModel
import bx.app.data.model.TextSideModel

@Entity(
    tableName = "audio_side",
    foreignKeys = [ForeignKey(
        entity = CardEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("card_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class AudioSideEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val path: String,
    @ColumnInfo(name = "file_mame") val fileName: String,
    val side: Int,
    @ColumnInfo(name = "card_id") val cardId: Long,
) : BaseEntity() {
    override fun toModel(): AudioSideModel {
        return AudioSideModel(
            id = this.id,
            path = this.path,
            fileName = this.fileName,
            side = if (this.side == 0) CardType.Text else CardType.Audio,
            cardId = this.cardId
        )
    }
}