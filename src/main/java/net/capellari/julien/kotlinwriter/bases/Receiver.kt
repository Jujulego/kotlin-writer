package net.capellari.julien.kotlinwriter.bases

import com.squareup.kotlinpoet.TypeName

interface Receiver {
    // Methods
    fun receiver(type: TypeName)
}