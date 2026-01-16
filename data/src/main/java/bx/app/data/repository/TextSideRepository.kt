package bx.app.data.repository

import bx.app.data.local.AppDatabase
import bx.app.data.local.entity.TextSideEntity
import bx.app.data.model.TextSideModel

class TextSideRepository(private val database: AppDatabase) {
    private val baseRepo = BaseRepository<TextSideEntity>(database.textSideDao())

    fun getTextById(id: Long) = database.textSideDao().getTextById(id)

    suspend fun getById(id: Long) = baseRepo.getById(id) as TextSideModel
    suspend fun insert(textSide: TextSideModel) = baseRepo.insert(textSide.toEntity())
    suspend fun update(textSide: TextSideModel) = baseRepo.update(textSide.toEntity())
    suspend fun delete(textSide: TextSideModel) = baseRepo.delete(textSide.toEntity())
}