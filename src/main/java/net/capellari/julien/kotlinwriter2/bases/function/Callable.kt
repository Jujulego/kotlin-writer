package net.capellari.julien.kotlinwriter2.bases.function

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import net.capellari.julien.kotlinwriter2.bases.Annotable
import net.capellari.julien.kotlinwriter2.bases.Codable
import net.capellari.julien.kotlinwriter2.bases.Modifiable
import net.capellari.julien.kotlinwriter2.bases.Wrapper

interface Callable: Wrapper<FunSpec>, Annotable, Modifiable, Codable {
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
    override fun add(code: CodeBlock) {
        builder.addCode(code)
    }
}