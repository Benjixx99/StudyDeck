package bx.app.data.local.entity

import bx.app.data.model.BaseModel

abstract class BaseEntity() {
    abstract fun toModel(): BaseModel
}