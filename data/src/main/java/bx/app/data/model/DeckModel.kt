package bx.app.data.model

import bx.app.data.local.entity.DeckEntity

data class DeckModel(
    val id: Long = 0,
    val name: String,
    val description: String?,
    val color: Long,
    val learnBothSides: Boolean,
    val onFailing: Int
) : BaseModel() {
    override fun toEntity(): DeckEntity {
        return DeckEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            color = this.color,
            learnBothSides = this.learnBothSides,
            onFailing = this.onFailing
        )
    }
}