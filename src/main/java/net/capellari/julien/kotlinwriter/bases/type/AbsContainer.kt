package net.capellari.julien.kotlinwriter.bases.type

import net.capellari.julien.kotlinwriter.bases.Annotable
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property

abstract class AbsContainer: Annotable {
    // Methods
    abstract fun add(func: AbsCallable)
    abstract fun add(type: AbsType)
    abstract fun add(prop: Property)
}