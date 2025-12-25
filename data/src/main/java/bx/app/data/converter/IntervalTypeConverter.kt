package bx.app.data.converter

import androidx.room.TypeConverter
import bx.app.data.enums.IntervalType

internal class IntervalTypeConverter {
    @TypeConverter
    fun fromEnum(type: IntervalType): Int = type.ordinal

    @TypeConverter
    fun toEnum(value: Int): IntervalType = when (value) {
        0 -> IntervalType.WEEK
        1 -> IntervalType.MONTH
        2 -> IntervalType.YEAR
        else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: $value")
    }
}