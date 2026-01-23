package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.enums.CardSide
import bx.app.data.local.entity.AudioSideEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AudioSideDao : BaseDao<AudioSideEntity> {
    @Query("SELECT * FROM audio_side")
    override fun observeAll(): Flow<List<AudioSideEntity>>

    @Query("SELECT * FROM audio_side")
    override suspend fun getAll(): List<AudioSideEntity>

    @Query("SELECT * FROM audio_side WHERE id = :id")
    override suspend fun getById(id: Long): AudioSideEntity

    @Query("SELECT file_mame FROM audio_side WHERE id = :id")
    fun getFileNameById(id: Long): Flow<String?>

    @Query("SELECT path FROM audio_side WHERE id = :id")
    fun getPathById(id: Long): Flow<String?>

    @Query("DELETE FROM audio_side")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM audio_side WHERE id = :id")
    fun countById(id: Long): Int

    @Query("SELECT id FROM audio_side WHERE card_id = :id AND side = :cardSide")
    suspend fun getAudioSideIdByCardId(id: Long, cardSide: CardSide): Long?
}