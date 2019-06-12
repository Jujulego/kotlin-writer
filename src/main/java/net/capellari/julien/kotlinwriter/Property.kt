package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.*
import net.capellari.julien.kotlinwriter.function.AbsFunction
import net.capellari.julien.kotlinwriter.bases.AbsWrapper
import net.capellari.julien.kotlinwriter.interfaces.Annotable
import net.capellari.julien.kotlinwriter.interfaces.Modifiable
import net.capellari.julien.kotlinwriter.function.Parameters
import kotlin.reflect.KClass

@KotlinMarker
class Property(name: String, type: TypeName):
        AbsWrapper<PropertySpec,PropertySpec.Builder>(PropertySpec.builder(name, type)),
        Annotable, Modifiable {

    // Propriétés
    override val spec get() = builder.build()

    // Méthodes
    // - annotations
    override fun annotation(type: ClassName) {
        builder.addAnnotation(type)
    }

    // - modifiers
    override fun modifiers(vararg modifiers: KModifier) {
        builder.addModifiers(*modifiers)
    }

    // - accesseurs
    fun getter(build: Getter.() -> Unit) {
        builder.getter(Getter().apply(build).spec)
    }

    fun setter(build: Setter.() -> Unit) {
        builder.getter(Setter().apply(build).spec)
    }

    // Classes
    class Getter : AbsFunction(FunSpec.getterBuilder())
    class Setter : AbsFunction(FunSpec.setterBuilder()), Parameters
}