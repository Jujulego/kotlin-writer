package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.bases.*

@KotlinMarker
class Parameter(override val name: String, val type: TypeName)
    : Wrapper<ParameterSpec>, Named, Annotable, Modifiable {

    // Attributes
    val builder = ParameterSpec.builder(name, type)

    // Properties
    override val spec get() = builder.build()

    // Methods
    fun default(value: Named)  { builder.defaultValue(value.name) }
    fun default(value: Number) { builder.defaultValue(value.toString()) }
    fun default(value: String) { builder.defaultValue(value) }

    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun toString() = name
}