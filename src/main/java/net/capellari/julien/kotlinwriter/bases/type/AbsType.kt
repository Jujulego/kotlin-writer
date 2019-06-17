package net.capellari.julien.kotlinwriter.bases.type

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property

abstract class AbsType(override val builder: TypeSpec.Builder, pkg: String, name: String): AbsContainer(pkg, name), Type {
    // Properties
    override val containerName get() = fullName

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