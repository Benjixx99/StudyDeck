package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.LevelEntity
import bx.app.data.model.LevelModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class LevelRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<LevelEntity>(database.levelDao())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeById(idFlow: Flow<Long>): Flow<List<LevelModel>> =
        idFlow.flatMapLatest { id ->
            database.levelDao().observeById(id).map { list -> list.map { it.toModel() } }
        }

    suspend fun getById(id: Long) = baseRepo.getById(id) as LevelModel
    suspend fun insert(level: LevelModel) = baseRepo.insert(level.toEntity())
    suspend fun update(level: LevelModel) = baseRepo.update(level.toEntity())
    suspend fun delete(level: LevelModel) = baseRepo.delete(level.toEntity())

    suspend fun upsert(level: LevelModel): Long {
        if (level.id <= 0)
            return insert(level)
        else {
            update(level)
            return level.id
        }
    }
}