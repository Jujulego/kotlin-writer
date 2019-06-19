package net.capellari.julien.kotlinwriter.bases.function

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import net.capellari.julien.kotlinwriter.TypeParameter
import net.capellari.julien.kotlinwriter.bases.*

interface Callable: Wrapper<FunSpec>, Annotable, Templatable, Modifiable, Codable {
    // Attributes
    val builder: FunSpec.Builder

    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun add(tparam: TypeParameter) {
        builder.addTypeVariable(tparam.typeName)
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