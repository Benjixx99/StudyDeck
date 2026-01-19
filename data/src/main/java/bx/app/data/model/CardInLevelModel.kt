package bx.app.data.model

import bx.app.data.local.entity.CardInLevelEntity
import java.time.LocalDateTime

data class CardInLevelModel(
    val cardId: Long,
    val levelId: Long,
    /**
     * Is only important if the column [bx.app.data.local.entity.DeckEntity.learnBothSides] is true
     */
    val lastTimeLearnedFront: Boolean = false,
    val lastTimeLearnedDate: LocalDateTime? = null,
) : BaseModel() {
    override fun toEntity(): CardInLevelEntity {
        return CardInLevelEntity(
            cardId = this.cardId,
            levelId = this.levelId,
            lastTimeLearnedFront = this.lastTimeLearnedFront,
            lastTimeLearnedDate = this.lastTimeLearnedDate
        )
    }
}