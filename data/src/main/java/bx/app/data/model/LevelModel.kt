package bx.app.data.model

import bx.app.data.enums.IntervalType
import bx.app.data.local.entity.LevelEntity

data class LevelModel(
    override val id: Long = 0,
    val name: String,
    val intervalNumber: Int,
    val intervalType: IntervalType,
    val deckId: Long,
) : IdentifiedModel() {
    override fun toEntity(): LevelEntity {
        return LevelEntity(
            id = this.id,
            name = this.name,
            intervalNumber = this.intervalNumber,
            intervalType = this.intervalType,
            deckId = this.deckId
        )
    }
}