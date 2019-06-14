package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.CodeBlock
import net.capellari.julien.kotlinwriter2.bases.AbsCodable
import net.capellari.julien.kotlinwriter2.bases.Wrapper

@KotlinMarker
class Code: AbsCodable(), Wrapper<CodeBlock> {
    // Attributes
    val builder = CodeBlock.builder()

    // Properties
    override val spec: CodeBlock get() {
        this.buildFlow()
        return builder.build()
    }

    // Methods
    override fun add(code: String) {
        this.buildFlow()
        builder.add(code)
    }
    override fun add(code: CodeBlock) {
        this.buildFlow()
        builder.add(code)
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