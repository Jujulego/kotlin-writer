package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.interfaces.Named
import net.capellari.julien.kotlinwriter2.interfaces.function.*

class Function(override val name: String): Named, Builder, Receiver, Parameters, Return {
    // Attributes
    override val builder = FunSpec.builder(name)

    // Methods
    override fun toString() = name
}