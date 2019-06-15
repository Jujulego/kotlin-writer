package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.Named
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.type.AbsType

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