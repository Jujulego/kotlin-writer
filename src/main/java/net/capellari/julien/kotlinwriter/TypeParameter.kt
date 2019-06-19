package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import net.capellari.julien.kotlinwriter.bases.Modifiable
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.type.Type

class TypeParameter(override val name: String) : Type, Named, Modifiable {
    // Attributes
    var reified = false
    val bounds = mutableListOf<TypeName>()
    private val modifiers = mutableListOf<KModifier>()

    // Properties
    override val typeName: TypeVariableName
        get() = TypeVariableName(name).copy(reified = reified, bounds = bounds)

    // Methods
    override fun modifier(modifier: KModifier) {
        if (modifier == KModifier.REIFIED) {
            reified = true
        } else {
            modifiers.add(modifier)
        }
    }

    override fun toString() = name
}