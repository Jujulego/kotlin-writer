package net.capellari.julien.kotlinwriter2.bases

import com.squareup.kotlinpoet.TypeName

interface Receiver {
    // Methods
    fun receiver(type: TypeName)
}