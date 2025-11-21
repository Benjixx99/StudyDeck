package bx.app.data.model

abstract class IdentifiedModel() : BaseModel() {
    abstract val id: Long
}