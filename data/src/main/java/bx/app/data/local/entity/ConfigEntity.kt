package bx.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import bx.app.data.enums.ConfigScope
import bx.app.data.model.ConfigModel

@Entity(
    tableName = "config",
    indices = [Index(value = ["key", "scope"], unique = true)]
)
internal data class ConfigEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val key: String,
    val scope: ConfigScope,
    val value: String,
) : BaseEntity() {
    override fun toModel(): ConfigModel {
        return ConfigModel(
            id = this.id,
            key = this.key,
            scope = this.scope,
            value = this.value
        )
    }
}
