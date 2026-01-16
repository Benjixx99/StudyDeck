package bx.app.core

fun Long.hasInvalidId(): Boolean = this.toLong() < IdPolicy.MIN_VALID_ID
fun Long.hasValidId(): Boolean = this.toLong() >= IdPolicy.MIN_VALID_ID
fun Long.isInsert(): Boolean = this.toLong() == IdPolicy.INSERT
