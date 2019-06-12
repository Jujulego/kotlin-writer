package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ParameterSpec
import net.capellari.julien.kotlinwriter.bases.AbsWrapper
import net.capellari.julien.kotlinwriter.interfaces.Annotable
import net.capellari.julien.kotlinwriter.interfaces.Modifiable
import kotlin.reflect.KClass

@KotlinMarker
class Parameter: AbsWrapper<ParameterSpec,ParameterSpec.Builder>,
        Annotable, Modifiable {

    // Propriétés
    override val spec get() = builder.build()

    // Constructeur
    constructor(name: String, type: TypeName): super(ParameterSpec.builder(name, type))
    constructor(name: String, type: KClass<*>): super(ParameterSpec.builder(name, type))

    constructor(type: TypeName): super(ParameterSpec.builder("", type))
    constructor(type: KClass<*>): super(ParameterSpec.builder("", type))

    // Méthodes
    // - annotations
    override fun annotation(type: ClassName) {
        builder.addAnnotation(type)
    }

    // - modifiers
    override fun modifiers(vararg modifiers: KModifier) {
        builder.addModifiers(*modifiers)
    }

    // - default value
    fun default(format: String, vararg args: Any?) {
        builder.defaultValue(format, *args)
    }

    fun default(build: Code.() -> Unit) {
        builder.defaultValue(Code().apply(build).spec)
    }
}

// Parameters
@Suppress("ClassName")
object unnamed

infix fun String.of(type: TypeName)  = Parameter(this, type)
infix fun String.of(type: KClass<*>) = Parameter(this, type)

infix fun unnamed.of(type: TypeName)  = Parameter(type)
infix fun unnamed.of(type: KClass<*>) = Parameter(type)