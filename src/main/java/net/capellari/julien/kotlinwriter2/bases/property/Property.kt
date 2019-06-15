package net.capellari.julien.kotlinwriter2.bases.property

import com.squareup.kotlinpoet.PropertySpec
import net.capellari.julien.kotlinwriter2.Parameter
import net.capellari.julien.kotlinwriter2.bases.Annotable
import net.capellari.julien.kotlinwriter2.bases.Modifiable
import net.capellari.julien.kotlinwriter2.bases.Named
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable

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