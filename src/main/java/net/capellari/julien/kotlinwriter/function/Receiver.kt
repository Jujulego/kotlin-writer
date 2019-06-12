package net.capellari.julien.kotlinwriter.function

import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass

interface Receiver : Wrapper {
    // Méthodes
    fun receiver(type: TypeName) {
        builder.receiver(type)
    }
    fun receiver(type: KClass<*>) {
        builder.receiver(type)
    }
}