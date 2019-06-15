package net.capellari.julien.kotlinwriter2.bases.function

import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.bases.Receiver

interface Receiver: Callable, Receiver {
    // Methods
    override fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}