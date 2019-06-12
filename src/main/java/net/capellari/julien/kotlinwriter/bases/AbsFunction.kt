package net.capellari.julien.kotlinwriter.bases

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeVariableName
import net.capellari.julien.kotlinwriter.Code
import net.capellari.julien.kotlinwriter.ControlFlow
import net.capellari.julien.kotlinwriter.KotlinMarker
import net.capellari.julien.kotlinwriter.TypeParameter
import net.capellari.julien.kotlinwriter.interfaces.Annotable
import net.capellari.julien.kotlinwriter.interfaces.Modifiable
import net.capellari.julien.kotlinwriter.interfaces.Codable
import net.capellari.julien.kotlinwriter.interfaces.Templatable
import kotlin.reflect.KClass

@KotlinMarker
abstract class AbsFunction(builder: FunSpec.Builder):
        AbsWrapper<FunSpec, FunSpec.Builder>(builder),
        Annotable, Modifiable,
        Templatable, Codable<AbsFunction> {

    // Propriétés
    override val spec get() = builder.build()

    // Méthodes
    // - annotations
    override fun annotation(type: ClassName) {
        builder.addAnnotation(type)
    }
    override fun annotation(type: KClass<*>) {
        builder.addAnnotation(type)
    }

    // - modifiers
    override fun modifiers(vararg modifiers: KModifier) {
        builder.addModifiers(*modifiers)
    }

    // - type paramaters
    override fun typeParameter(name: String, build: TypeParameter.() -> Unit)
            = TypeParameter(name).apply(build).spec.also { builder.addTypeVariable(it) }

    // - comments
    override fun comment(format: String, vararg args: Any) {
        builder.addComment(format, *args)
    }

    // - code
    fun code(build: Code.() -> Unit) {
        builder.addCode(Code().apply(build).spec)
    }

    override fun format(format: String, vararg args: Any) {
        builder.addStatement(format, *args)
    }

    override fun beginFlow(format: String, vararg args: Any) {
        builder.beginControlFlow(format, *args)
    }

    override fun nextFlow(format: String, vararg args: Any) {
        builder.nextControlFlow(format, *args)
    }

    override fun endFlow() {
        builder.endControlFlow()
    }
}