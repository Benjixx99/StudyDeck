package bx.app.data.enums

import bx.app.data.converter.BaseEnumConverter

enum class CardSideType { TEXT, AUDIO }

internal class CardTypeConverter : BaseEnumConverter<CardFailing>(CardFailing::class)