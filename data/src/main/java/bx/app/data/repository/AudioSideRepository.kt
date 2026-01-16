package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.AudioSideEntity
import bx.app.data.model.AudioSideModel

class AudioSideRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<AudioSideEntity>(database.audioSideDao())

    fun getFileNameById(id: Long) = database.audioSideDao().getFileNameById(id)
    fun getPathById(id: Long) = database.audioSideDao().getPathById(id)

    suspend fun getById(id: Long) = baseRepo.getById(id) as AudioSideModel
    suspend fun insert(audioSide: AudioSideModel) = baseRepo.insert(audioSide.toEntity())
    suspend fun update(audioSide: AudioSideModel) = baseRepo.update(audioSide.toEntity())
    suspend fun delete(audioSide: AudioSideModel) = baseRepo.delete(audioSide.toEntity())
}