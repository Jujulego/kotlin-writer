package net.capellari.julien.kotlinwriter.bases.property

import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter.bases.Receiver

interface Receiver: Property, Receiver {
    // Methods
    override fun receiver(type: TypeName) {
        builder.receiver(type)
    }
}