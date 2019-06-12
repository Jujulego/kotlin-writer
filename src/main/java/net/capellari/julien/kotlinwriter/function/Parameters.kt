package net.capellari.julien.kotlinwriter.function

import net.capellari.julien.kotlinwriter.Parameter

interface Parameters : Wrapper {
    // Méthodes
    // - parameters
    fun parameter(param: Parameter, build: Parameter.() -> Unit = {}) {
        builder.addParameter(param.apply(build).spec)
    }

    fun parameters(params: Iterable<Parameter>) {
        params.map { parameter(it) }
    }
    fun parameters(vararg params: Parameter) {
        params.map { parameter(it) }
    }
}