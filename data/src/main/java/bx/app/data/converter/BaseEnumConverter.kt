package bx.app.data.converter

import androidx.room.TypeConverter
import kotlin.reflect.KClass

internal abstract class BaseEnumConverter<E : Enum<E>>(private val enumClass: KClass<E>) {
    @TypeConverter
    fun fromEnum(type: E): String = type.name

    @TypeConverter
    fun toEnum(value: String): E = java.lang.Enum.valueOf(enumClass.java, value)
}