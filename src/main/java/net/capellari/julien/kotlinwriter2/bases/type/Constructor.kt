package net.capellari.julien.kotlinwriter2.bases.type

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.function.Parameters

class Constructor: AbsCallable(FunSpec.constructorBuilder()), Parameters {
    // Methods
    fun this_(vararg args: String) {
        builder.callThisConstructor(*args)
    }

    fun super_(vararg args: String) {
        builder.callSuperConstructor(*args)
    }
}