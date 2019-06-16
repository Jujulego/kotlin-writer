package net.capellari.julien.kotlinwriter.bases.function

import com.squareup.kotlinpoet.TypeName

interface Return: Callable {
    // Method
    fun returns(type: TypeName) {
        builder.returns(type)
    }
}