package net.capellari.julien.kotlinwriter2.interfaces.function

import net.capellari.julien.kotlinwriter2.Parameter

interface Parameters: Builder {
    // Methods
    fun parameter(param: Parameter, build: Parameter.() -> Unit = {}): Parameter {
        param.build()
        builder.addParameter(param.spec)

        return param
    }

    fun parameters(vararg params: Parameter)    = params.map { parameter(it) }
    fun parameters(params: Iterable<Parameter>) = params.map { parameter(it) }
}