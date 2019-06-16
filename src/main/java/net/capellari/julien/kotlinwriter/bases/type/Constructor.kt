package net.capellari.julien.kotlinwriter.bases.type

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.function.Parameters

class Constructor: AbsCallable(FunSpec.constructorBuilder()), Parameters {
    // Methods
    fun this_(vararg args: String) {
        builder.callThisConstructor(*args)
    }

    fun super_(vararg args: String) {
        builder.callSuperConstructor(*args)
    }
}