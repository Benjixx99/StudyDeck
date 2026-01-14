package bx.app.data.converter

import androidx.room.TypeConverter
import bx.app.data.enums.IntervalType

internal class IntervalTypeConverter {
    @TypeConverter
    fun fromEnum(type: IntervalType): Int = type.days

    @TypeConverter
    fun toEnum(value: Int): IntervalType = when (value) {
        IntervalType.DAY.days -> IntervalType.DAY
        IntervalType.WEEK.days -> IntervalType.WEEK
        IntervalType.MONTH.days -> IntervalType.MONTH
        IntervalType.YEAR.days -> IntervalType.YEAR
        else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: $value")
    }
}