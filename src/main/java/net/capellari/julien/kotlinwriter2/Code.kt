package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.CodeBlock
import net.capellari.julien.kotlinwriter2.interfaces.Codable
import net.capellari.julien.kotlinwriter2.interfaces.Wrapper

@KotlinMarker
class Code: Wrapper<CodeBlock>, Codable {
    // Attributes
    val builder = CodeBlock.builder()

    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun add(code: String) {
        builder.add(code)
    }
}