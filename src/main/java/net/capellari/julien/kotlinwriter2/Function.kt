package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.bases.Named
import net.capellari.julien.kotlinwriter2.bases.function.*

class Function(override val name: String): AbsCallable(FunSpec.builder(name)), Named, Receiver, Parameters, Return {
    // Methods
    override fun toString() = name
}