package net.capellari.julien.kotlinwriter2.bases.property

import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.bases.Receiver

interface Receiver: Property, Receiver {
    // Methods
    override fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}