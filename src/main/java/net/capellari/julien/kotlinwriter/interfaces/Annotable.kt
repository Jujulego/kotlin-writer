package net.capellari.julien.kotlinwriter.interfaces

import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

interface Annotable {
    // Méthodes
    fun annotation(type: ClassName)
}