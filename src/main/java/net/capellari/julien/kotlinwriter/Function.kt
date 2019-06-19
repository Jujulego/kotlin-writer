package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.Templatable
import net.capellari.julien.kotlinwriter.bases.function.*

class Function(override val name: String): AbsCallable(FunSpec.builder(name)),
        Named, Receiver, Parameters, Return {

    // Methods
    override fun toString() = name
}