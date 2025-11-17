package bx.app.data.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal class LocalDateTimeConverter {
    private val zone: ZoneId = ZoneId.systemDefault()

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): Long? = localDateTime?.atZone(zone)?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? = value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), zone) }
}