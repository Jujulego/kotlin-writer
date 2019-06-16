package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.type.AbsType

class Object(override val name: String): AbsType(TypeSpec.objectBuilder(name)), Named {
    // Methods
    override fun toString() = name
}