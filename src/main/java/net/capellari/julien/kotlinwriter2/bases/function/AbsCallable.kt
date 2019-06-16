package net.capellari.julien.kotlinwriter2.bases.function

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.bases.AbsCodable

abstract class AbsCallable(override val builder: FunSpec.Builder): AbsCodable(), Callable {
    // Properties
    override val spec: FunSpec get() {
        this.buildFlow()
        return builder.build()
    }

    // Methods
    override fun add(code: String) {
        this.buildFlow()
        builder.addCode(code + "\n")
    }
    override fun add(code: CodeBlock) {
        this.buildFlow()
        builder.addCode(code)
    }

    override fun beginFlow(code: String) {
        builder.beginControlFlow(code)
    }
    override fun nextFlow(code: String) {
        builder.nextControlFlow(code)
    }
    override fun endFlow() {
        builder.endControlFlow()
    }
}