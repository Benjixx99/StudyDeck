package bx.app.data.repository

import bx.app.data.local.dao.BaseDao
import bx.app.data.local.entity.BaseEntity
import bx.app.data.model.BaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal open class BaseRepository<T : BaseEntity> internal constructor(private val dao: BaseDao<T>) {
    val flowList: Flow<List<BaseModel>> = dao.observeAll().map { list -> list.map { it.toModel() } }
    suspend fun getById(id: Long): BaseModel = dao.getById(id).toModel()
    suspend fun insert(entity: T): Long = dao.insert(entity)
    suspend fun update(entity: T): Unit = dao.update(entity)
    suspend fun delete(entity: T): Unit = dao.delete(entity)
    suspend fun deleteAll() = dao.deleteAll()
}