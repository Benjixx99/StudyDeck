package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.local.entity.AudioSideEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AudioSideDao : BaseDao<AudioSideEntity> {
    @Query("SELECT * FROM audio_side")
    override fun observeAll(): Flow<List<AudioSideEntity>>

    @Query("SELECT * FROM audio_side WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): AudioSideEntity

    @Query("DELETE FROM audio_side")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM audio_side WHERE id = :id")
    fun countById(id: Long): Int
}