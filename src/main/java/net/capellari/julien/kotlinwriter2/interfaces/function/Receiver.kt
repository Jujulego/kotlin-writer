package net.capellari.julien.kotlinwriter2.interfaces.function

import com.squareup.kotlinpoet.TypeName

interface Receiver: Builder {
    // Methods
    fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}