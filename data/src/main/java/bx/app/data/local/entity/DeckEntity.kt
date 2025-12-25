package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bx.app.data.enums.CardFailing
import bx.app.data.model.DeckModel

@Entity(tableName = "deck")
internal data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String?,
    val color: Long = 0xFFFF0000,
    @ColumnInfo(name = "learn_both_sides")
    val learnBothSides: Boolean = false,
    @ColumnInfo(name = "on_failing")
    val onFailing: CardFailing = CardFailing.MOVE_TO_START,
) : BaseEntity() {
    override fun toModel(): DeckModel {
        return DeckModel(
            id = this.id,
            name = this.name,
            description = this.description,
            color = this.color,
            learnBothSides = this.learnBothSides,
            onFailing = this.onFailing
        )
    }
}