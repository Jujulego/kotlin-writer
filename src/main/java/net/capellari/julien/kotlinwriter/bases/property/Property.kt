package net.capellari.julien.kotlinwriter.bases.property

import com.squareup.kotlinpoet.PropertySpec
import net.capellari.julien.kotlinwriter.Parameter
import net.capellari.julien.kotlinwriter.bases.Annotable
import net.capellari.julien.kotlinwriter.bases.Modifiable
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.Wrapper
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable

interface Property: Wrapper<PropertySpec>, Named, Annotable, Modifiable {
    // Attributes
    val builder: PropertySpec.Builder

    // Properties
    override val spec get() = builder.build()
    var mutable: Boolean

    // Methods
    fun init(code: String)
    fun getter(build: AbsCallable.() -> Unit)
    fun setter(parameter: Parameter, build: AbsCallable.(Parameter) -> Unit)
}