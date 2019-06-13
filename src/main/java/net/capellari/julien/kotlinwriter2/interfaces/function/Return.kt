package net.capellari.julien.kotlinwriter2.interfaces.function

import com.squareup.kotlinpoet.TypeName

interface Return: Builder {
    // Method
    fun returns(type: TypeName) {
        builder.returns(type)
    }
}