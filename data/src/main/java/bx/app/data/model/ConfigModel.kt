package bx.app.data.model

import bx.app.data.enums.ConfigScope
import bx.app.data.local.entity.BaseEntity
import bx.app.data.local.entity.ConfigEntity

data class ConfigModel(
    override val id: Long = 0,
    val key: String,
    val scope: ConfigScope,
    val value: String
) : IdentifiedModel() {
    override fun toEntity(): BaseEntity {
        return ConfigEntity(
            id = this.id,
            key = this.key,
            scope = this.scope,
            value = this.value
        )
    }
}
