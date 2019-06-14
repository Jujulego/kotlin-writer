package net.capellari.julien.kotlinwriter2.bases

import net.capellari.julien.kotlinwriter2.Flow

abstract class AbsCodable: Codable {
    // Attributes
    private var flow: Flow? = null

    // Methods
    internal fun buildFlow() {
        flow?.let {
            flow = null
            it.build(this)
        }
    }

    private fun registerFlow(flow: Flow) {
        buildFlow()
        this.flow = flow
    }

    internal abstract fun beginFlow(code: String)
    internal abstract fun nextFlow(code: String)
    internal abstract fun endFlow()

    fun flow(code: String, build: AbsCodable.() -> Unit)
            = Flow(code, build).also { registerFlow(it) }
}