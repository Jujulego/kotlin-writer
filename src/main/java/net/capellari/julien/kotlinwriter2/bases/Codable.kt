package net.capellari.julien.kotlinwriter2.bases

interface Codable {
    // Operators
    operator fun plus(code: String) = also { add(code) }
    operator fun plus(code: Named)  = also { add(code.name) }
    operator fun String.unaryPlus() = this@Codable.also { add(this) }

    // Methods
    fun add(code: String)
}