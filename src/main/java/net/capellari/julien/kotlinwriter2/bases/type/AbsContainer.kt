package net.capellari.julien.kotlinwriter2.bases.type

import net.capellari.julien.kotlinwriter2.bases.Annotable
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.property.Property

abstract class AbsContainer: Annotable {
    // Methods
    abstract fun add(func: AbsCallable)
    abstract fun add(type: AbsType)
    abstract fun add(prop: Property)
}