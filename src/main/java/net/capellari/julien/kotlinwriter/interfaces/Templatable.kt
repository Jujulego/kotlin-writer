package net.capellari.julien.kotlinwriter.interfaces

import com.squareup.kotlinpoet.TypeVariableName
import net.capellari.julien.kotlinwriter.TypeParameter

interface Templatable {
    // Méthodes
    // - type parameters
    fun typeParameter(name: String, build: TypeParameter.() -> Unit = {}): TypeVariableName
}