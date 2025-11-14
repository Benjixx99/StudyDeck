package bx.app.data.enums

import bx.app.data.converter.BaseEnumConverter

enum class CardSide { FRONT, BACK }

internal class CardSideConverter : BaseEnumConverter<CardSide>(CardSide::class)
