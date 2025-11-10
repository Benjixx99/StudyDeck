package bx.app.data.model

import bx.app.data.local.entity.BaseEntity

abstract class BaseModel() {
    internal abstract fun toEntity() : BaseEntity
}