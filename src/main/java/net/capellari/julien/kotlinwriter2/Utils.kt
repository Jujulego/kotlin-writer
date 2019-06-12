package net.capellari.julien.kotlinwriter2

import net.capellari.julien.kotlinwriter2.interfaces.Named

fun Any.asKotlinValue()
    = when (this) {
        is String -> this
        is Named -> name
        else -> toString()
    }