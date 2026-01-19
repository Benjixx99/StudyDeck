package bx.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import bx.app.data.model.CardInLevelModel
import java.time.LocalDateTime

@Entity(
    tableName = "card_in_level",
    primaryKeys = ["card_id", "level_id"],
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LevelEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("level_id"),
            onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["card_id"], unique = true)]
)
internal data class CardInLevelEntity(
    @ColumnInfo(name = "card_id")
    val cardId: Long,
    @ColumnInfo(name = "level_id")
    val levelId: Long,
    @ColumnInfo(name = "last_time_Learned_front")
    val lastTimeLearnedFront: Boolean = false,
    @ColumnInfo(name = "last_time_learned_date")
    val lastTimeLearnedDate: LocalDateTime? = null
) : BaseEntity() {
    override fun toModel(): CardInLevelModel {
        return CardInLevelModel(
            cardId = this.cardId,
            levelId = this.levelId,
            lastTimeLearnedFront = this.lastTimeLearnedFront,
            lastTimeLearnedDate = this.lastTimeLearnedDate
        )
    }
}

