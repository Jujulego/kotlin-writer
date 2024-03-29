package net.capellari.julien.kotlinwriter

import net.capellari.julien.kotlinwriter.bases.AbsCodable

class Flow(code: String, build: AbsCodable.() -> Unit) {
    // Attributes
    private val parts = mutableListOf(code to build)

    // Methods
    internal fun build(block: AbsCodable) {
        var first = true

        parts.forEach { (code, build) ->
            if (first) {
                block.beginFlow(code)
                first = false
            } else {
                block.nextFlow(code)
            }

            block.(build)()
            block.buildFlow()
        }

        block.endFlow()
    }

    fun next(code: String, build: AbsCodable.() -> Unit) = also { parts.add(code to build) }
}