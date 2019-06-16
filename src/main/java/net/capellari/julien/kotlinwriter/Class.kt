package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.type.AbsType

class Class(override val name: String) : AbsType(TypeSpec.classBuilder(name)), Named {
    // Methods
    override fun toString() = name

    fun companion(build: Companion.() -> Unit)
            = Companion().also {
                it.build()
                builder.addType(it.spec)
            }

    // Classes
    class Companion: AbsType(TypeSpec.companionObjectBuilder())
}