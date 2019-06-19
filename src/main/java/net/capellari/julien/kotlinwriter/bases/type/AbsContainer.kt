package net.capellari.julien.kotlinwriter.bases.type

import net.capellari.julien.kotlinwriter.bases.Annotable
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property

abstract class AbsContainer(val pkg: String, override val name: String): Annotable, Named {
    // Attributes
    abstract val containerName: String
    private var isTemplating = false

    // Properties
    val fullName get() = "$pkg.$name"

    // Methods
    fun add(type: AbsType) {
        if (!isTemplating) addType(type)
    }
    fun add(prop: Property) {
        if (!isTemplating) addProperty(prop)
    }
    fun add(func: AbsCallable) {
        if (!isTemplating) addCallable(func)
    }

    fun <T> templating(f: () -> T): T {
        isTemplating = true

        try {
            return f()
        } finally {
            isTemplating = false
        }
    }

    protected abstract fun addType(type: AbsType)
    protected abstract fun addProperty(prop: Property)
    protected abstract fun addCallable(func: AbsCallable)

    override fun toString() = fullName
}