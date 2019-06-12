package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

@KotlinMarker
class Parameter(override val name: String, val type: TypeName)
    : Wrapper<ParameterSpec>, Named {

    // Attributes
    val builder = ParameterSpec.builder(name, type)

    // Properties
    override val spec: ParameterSpec
        get() = builder.build()

    // Methods
    fun default(value: Any) {
        builder.defaultValue(value.asKotlinValue())
    }

    override fun toString() = name
}