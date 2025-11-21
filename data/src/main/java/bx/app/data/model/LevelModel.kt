package bx.app.data.model

import bx.app.data.local.entity.LevelEntity

data class LevelModel(
    override val id: Long = 0,
    val intervalNumber: Int,
    val intervalType: Long,
    val deckId: Long,
) : IdentifiedModel() {
    override fun toEntity(): LevelEntity {
        return LevelEntity(
            id = this.id,
            intervalNumber = this.intervalNumber,
            intervalType = this.intervalType,
            deckId = this.deckId
        )
    }
}