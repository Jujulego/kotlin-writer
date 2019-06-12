package net.capellari.julien.kotlinwriter.function

import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass

interface Returns : Wrapper {
    // Méthodes
    fun returns(type: TypeName) {
        builder.returns(type)
    }
    fun returns(type: KClass<*>) {
        builder.returns(type)
    }
}