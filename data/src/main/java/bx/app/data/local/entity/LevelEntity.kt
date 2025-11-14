package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import bx.app.data.model.LevelModel

@Entity(
    tableName = "level",
    foreignKeys = [ForeignKey(
        entity = DeckEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("deck_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class LevelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "interval_number")
    val intervalNumber: Int,
    @ColumnInfo(name = "interval_type")
    val intervalType: Long,
    @ColumnInfo(name = "deck_id")
    val deckId: Long,
) : BaseEntity() {
    override fun toModel(): LevelModel {
        return LevelModel(
            id = this.id,
            intervalNumber = this.intervalNumber,
            intervalType = this.intervalType,
            deckId = this.deckId
        )
    }
}
