package bx.app.data.enums

import bx.app.data.converter.BaseEnumConverter

enum class CardFailing { MOVE_TO_START, MOVE_ONE_LEVE_DOWN, STAY_ON_CURRENT_LEVEL }

internal class CardFailingConverter : BaseEnumConverter<CardFailing>(CardFailing::class)