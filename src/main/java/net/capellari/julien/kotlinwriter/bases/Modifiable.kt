package net.capellari.julien.kotlinwriter.bases

import com.squareup.kotlinpoet.KModifier

interface Modifiable {
    // Methods
    fun modifier(modifier: KModifier)
    fun modifier(vararg modifiers: KModifier) = modifiers.forEach { modifier(it) }
}