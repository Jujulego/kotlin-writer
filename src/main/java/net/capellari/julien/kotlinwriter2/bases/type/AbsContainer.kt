package net.capellari.julien.kotlinwriter2.bases.type

import net.capellari.julien.kotlinwriter2.bases.Annotable
import net.capellari.julien.kotlinwriter2.bases.Modifiable
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable

abstract class AbsContainer: Annotable, Modifiable {
    // Methods
    abstract fun add(func: AbsCallable)
}