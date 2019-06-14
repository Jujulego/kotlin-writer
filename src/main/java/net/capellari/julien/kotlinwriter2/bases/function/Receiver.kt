package net.capellari.julien.kotlinwriter2.bases.function

import com.squareup.kotlinpoet.TypeName

interface Receiver: Callable {
    // Methods
    fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}