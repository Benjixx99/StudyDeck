package bx.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import bx.app.data.local.entity.TextSideEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TextSideDao : BaseDao<TextSideEntity> {
    @Query("SELECT * FROM text_side")
    override fun observeAll(): Flow<List<TextSideEntity>>

    @Query("SELECT * FROM text_side WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): TextSideEntity

    @Query("SELECT text FROM text_side WHERE id = :id")
    fun getTextById(id: Long): Flow<String?>

    @Query("DELETE FROM text_side")
    override suspend fun deleteAll()

    @Query("SELECT count(*) FROM text_side WHERE id = :id")
    fun countById(id: Long): Int
}