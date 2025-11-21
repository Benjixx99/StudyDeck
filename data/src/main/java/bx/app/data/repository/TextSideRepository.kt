package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.TextSideEntity
import bx.app.data.model.TextSideModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TextSideRepository(database: AppDatabase) {
    private val baseRepo = BaseRepository<TextSideEntity>(database.textSideDao())

    fun getAll(): Flow<List<TextSideModel>> = baseRepo.flowList.map { it.filterIsInstance<TextSideModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as TextSideModel
    suspend fun insert(user: TextSideModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: TextSideModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: TextSideModel) = baseRepo.delete(user.toEntity())
}