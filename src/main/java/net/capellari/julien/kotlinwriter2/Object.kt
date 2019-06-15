package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.Named
import net.capellari.julien.kotlinwriter2.bases.type.AbsType

class Object(override val name: String): AbsType(TypeSpec.objectBuilder(name)), Named {
    // Methods
    override fun toString() = name
}