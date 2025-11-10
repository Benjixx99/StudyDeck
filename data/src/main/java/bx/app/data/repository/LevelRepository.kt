package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.LevelEntity
import bx.app.data.model.LevelModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LevelRepository internal constructor(database: AppDatabase) {
    private val baseRepo = BaseRepository<LevelEntity>(database.levelDao())

    fun getAll(): Flow<List<LevelModel>> = baseRepo.flowList.map { it.filterIsInstance<LevelModel>() }
    suspend fun getById(id: Long) = baseRepo.getById(id) as LevelModel
    suspend fun insert(user: LevelModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: LevelModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: LevelModel) = baseRepo.delete(user.toEntity())
}