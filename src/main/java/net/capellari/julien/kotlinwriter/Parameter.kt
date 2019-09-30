package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.*
import net.capellari.julien.kotlinwriter.bases.*

@KotlinMarker
class Parameter(override val name: String, val type: TypeName)
    : Wrapper<ParameterSpec>, Named, Annotable, Modifiable {

    // Attributes
    val builder = ParameterSpec.builder(name, type)
    var default: String? = null

    // Properties
    override val spec get() = builder.build()

    // Methods
    fun default(value: Named)  { default(value.name) }
    fun default(value: Number) { default(value.toString()) }
    fun default(value: String) {
        builder.defaultValue(value)
        default = value
    }

    override fun annotate(annotation: AnnotationSpec) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun toString() = name
}