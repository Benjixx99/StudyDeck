package bx.app.core

fun String.maxLength(length: Int): String {
    if (this.length > length) return this.substring(0, length)
    return this
}