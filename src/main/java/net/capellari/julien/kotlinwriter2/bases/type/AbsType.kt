package net.capellari.julien.kotlinwriter2.bases.type

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.property.Property

abstract class AbsType(override val builder: TypeSpec.Builder): AbsContainer(), Type {
    // Methods
    override fun add(func: AbsCallable) {
        builder.addFunction(func.spec)
    }
    override fun add(type: AbsType) {
        builder.addType(type.spec)
    }
    override fun add(prop: Property) {
        builder.addProperty(prop.spec)
    }
}