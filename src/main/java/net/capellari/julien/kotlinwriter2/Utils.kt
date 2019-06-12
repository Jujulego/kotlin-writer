package net.capellari.julien.kotlinwriter2

fun Any.asKotlinValue()
    = when (this) {
        is String -> this
        is Named -> name
        else -> toString()
    }