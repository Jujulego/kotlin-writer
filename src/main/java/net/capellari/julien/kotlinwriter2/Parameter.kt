package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.interfaces.*

@KotlinMarker
class Parameter(override val name: String, val type: TypeName)
    : Wrapper<ParameterSpec>, Named, Annotable, Modifiable {

    // Attributes
    val builder = ParameterSpec.builder(name, type)

    // Properties
    override val spec: ParameterSpec
        get() = builder.build()

    // Methods
    fun default(value: Any) {
        builder.defaultValue(value.asKotlinValue())
    }

    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun toString() = name
}