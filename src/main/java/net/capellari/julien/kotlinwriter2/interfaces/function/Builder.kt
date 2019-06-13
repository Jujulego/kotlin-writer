package net.capellari.julien.kotlinwriter2.interfaces.function

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import net.capellari.julien.kotlinwriter2.interfaces.Annotable
import net.capellari.julien.kotlinwriter2.interfaces.Codable
import net.capellari.julien.kotlinwriter2.interfaces.Modifiable
import net.capellari.julien.kotlinwriter2.interfaces.Wrapper

interface Builder: Wrapper<FunSpec>, Annotable, Modifiable, Codable {
    // Attributes
    val builder: FunSpec.Builder

    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun modifier(vararg modifiers: KModifier) {
        builder.addModifiers(*modifiers)
    }

    override fun add(code: String) {
        builder.addCode(code)
    }
}