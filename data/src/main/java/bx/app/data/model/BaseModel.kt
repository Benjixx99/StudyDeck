package bx.app.data.model

import bx.app.data.local.entity.BaseEntity

abstract class BaseModel() {
    abstract fun toEntity() : BaseEntity
}