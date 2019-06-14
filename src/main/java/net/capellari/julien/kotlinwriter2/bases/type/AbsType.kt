package net.capellari.julien.kotlinwriter2.bases.type

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable

abstract class AbsType(val builder: TypeSpec.Builder): AbsContainer(), Wrapper<TypeSpec> {
    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun add(func: AbsCallable) {
        builder.addFunction(func.spec)
    }
}