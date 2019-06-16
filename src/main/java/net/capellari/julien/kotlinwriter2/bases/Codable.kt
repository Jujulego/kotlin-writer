package net.capellari.julien.kotlinwriter2.bases

import com.squareup.kotlinpoet.CodeBlock

interface Codable {
    // Operators
    operator fun plus(code: String) = add(code)
    operator fun plus(code: Named)  = add(code.name)
    operator fun String.unaryPlus() = this@Codable.add(this)

    // Methods
    fun add(code: String)
    fun add(code: CodeBlock)
}