package net.capellari.julien.kotlinwriter2.bases.type

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.Annotable
import net.capellari.julien.kotlinwriter2.bases.Modifiable
import net.capellari.julien.kotlinwriter2.bases.Wrapper

interface Type: Wrapper<TypeSpec>, Annotable, Modifiable {
    // Attributs
    val builder: TypeSpec.Builder

    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    fun superclass(type: TypeName, vararg params: String) {
        builder.superclass(type)

        params.forEach {
            builder.addSuperclassConstructorParameter(it)
        }
    }
    fun superinterface(type: TypeName) {
        builder.addSuperinterface(type)
    }
}