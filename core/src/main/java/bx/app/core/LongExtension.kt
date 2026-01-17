package bx.app.core

fun Long.hasInvalidId(): Boolean = this.toLong() < IdPolicy.MIN_VALID_ID
fun Long.hasValidId(): Boolean = this.toLong() >= IdPolicy.MIN_VALID_ID
fun Long.hasInsertId(): Boolean = this.toLong() == IdPolicy.INSERT
fun Long.hasRandomId(): Boolean = this.toLong() == IdPolicy.RANDOM_ID
