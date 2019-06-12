package net.capellari.julien.kotlinwriter.interfaces

import com.squareup.kotlinpoet.KModifier

interface Modifiable {
    // Méthodes
    fun modifiers(vararg modifiers: KModifier)
}