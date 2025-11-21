package bx.app.data.model

import bx.app.data.local.entity.BaseEntity

data class LearnModel(
    val id: Int,
    val name: String,
    val description: String
) : BaseModel() {
    override fun toEntity(): BaseEntity {
        TODO("Not needed")
    }
}