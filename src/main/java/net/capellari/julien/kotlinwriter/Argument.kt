package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.CodeBlock
import net.capellari.julien.kotlinwriter.bases.Wrapper

class Argument(val name: String?, val value: String)
    : Wrapper<CodeBlock> {

    // Attributes
    val builder = CodeBlock.builder()

    // Properties
    override val spec get() = builder.build()

    // Constructor
    init {
        if (name != null) {
            builder.add("$name = $value")
        } else {
            builder.add(value)
        }
    }
}