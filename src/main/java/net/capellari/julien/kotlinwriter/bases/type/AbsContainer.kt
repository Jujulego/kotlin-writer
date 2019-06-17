package net.capellari.julien.kotlinwriter.bases.type

import net.capellari.julien.kotlinwriter.bases.Annotable
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property

abstract class AbsContainer(val pkg: String, override val name: String): Annotable, Named {
    // Properties
    val fullName get() = "$pkg.$name"
    abstract val containerName: String

    // Methods
    abstract fun add(func: AbsCallable)
    abstract fun add(type: AbsType)
    abstract fun add(prop: Property)

    override fun toString() = fullName
}