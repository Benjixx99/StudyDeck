package bx.app.data.repository

import bx.app.data.local.dao.BaseDao
import bx.app.data.local.entity.BaseEntity
import bx.app.data.model.BaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseRepository<T : BaseEntity> internal constructor(private val dao: BaseDao<T>) {
    protected val flowList: Flow<List<BaseModel>> = dao.observeAll().map { list -> list.map { it.toModel() } }
    protected suspend fun insert(entity: T): Long = dao.insert(entity)
    protected suspend fun update(entity: T): Unit = dao.update(entity)
    protected suspend fun delete(entity: T): Unit = dao.delete(entity)

    protected open suspend fun getById(id: Long): BaseModel = dao.getById(id).toModel()
    suspend fun deleteAll() = dao.deleteAll()
}