package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.model.AudioSideModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioSideRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<AudioSideEntity>(database.audioSideDao())

    fun getAll(): Flow<List<AudioSideModel>> = baseRepo.flowList.map { it.filterIsInstance<AudioSideModel>() }
    fun getFileNameById(id: Long) = database.audioSideDao().getFileNameById(id)

    suspend fun getById(id: Long) = baseRepo.getById(id) as AudioSideModel
    suspend fun insert(user: AudioSideModel) = baseRepo.insert(user.toEntity())
    suspend fun update(user: AudioSideModel) = baseRepo.update(user.toEntity())
    suspend fun delete(user: AudioSideModel) = baseRepo.delete(user.toEntity())
}