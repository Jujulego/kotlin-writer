package net.capellari.julien.kotlinwriter2.bases.function

import net.capellari.julien.kotlinwriter2.Parameter

interface Parameters: Callable {
    // Methods
    fun parameter(param: Parameter, build: Parameter.() -> Unit = {}): Parameter {
        param.build()
        builder.addParameter(param.spec)

        return param
    }

    fun parameters(vararg params: Parameter)    = params.map { parameter(it) }
    fun parameters(params: Iterable<Parameter>) = params.map { parameter(it) }
}