package bx.app.data.model

import bx.app.data.local.entity.BaseEntity

data class LearnModel(
    override val id: Long,
    val name: String,
    val description: String
) : IdentifiedModel() {
    override fun toEntity(): BaseEntity {
        TODO("Not needed")
    }
}