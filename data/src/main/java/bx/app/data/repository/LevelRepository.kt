package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.LevelEntity
import bx.app.data.model.LevelModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class LevelRepository(database: AppDatabase) {
    private val baseRepo = BaseRepository<LevelEntity>(database.levelDao())
    private val levelDao = database.levelDao()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeByDeckId(idFlow: Flow<Long>): Flow<List<LevelModel>> =
        idFlow.flatMapLatest { id ->
            levelDao.observeByDeckId(id).map { list -> list.map { it.toModel() } }
        }

    suspend fun getById(id: Long) = baseRepo.getById(id) as LevelModel
    suspend fun deleteById(id: Long) = levelDao.deleteById(id)
    suspend fun deleteByDeckId(id: Long) = levelDao.deleteByDeckId(id)

    suspend fun insert(level: LevelModel) = baseRepo.insert(level.toEntity())
    suspend fun upsert(level: LevelModel): Long {
        if (level.id <= 0) {
            return baseRepo.insert(level.toEntity())
        }
        else {
            baseRepo.update(level.toEntity())
            return level.id
        }
    }
}