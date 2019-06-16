package net.capellari.julien.kotlinwriter.bases.function

import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter.bases.Receiver

interface Receiver: Callable, Receiver {
    // Methods
    override fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}