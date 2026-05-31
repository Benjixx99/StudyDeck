package bx.app.data.repository

import bx.app.data.enums.ConfigScope
import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.ConfigEntity
import bx.app.data.model.ConfigModel

class ConfigRepository(private val database: AppDatabase) {
    private val configDao = database.configDao()

    suspend fun getValue(key: String, scope: ConfigScope): String {
        val value = configDao.getValue(key, scope)
        return if (value.isNullOrEmpty()) "" else value
    }
    suspend fun insert(config: ConfigModel) { configDao.insert(config.toEntity() as ConfigEntity) }
}