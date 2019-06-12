package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.AbsType

@KotlinMarker
open class Object internal constructor(builder: TypeSpec.Builder): AbsType(builder) {
    // Constucteurs
    constructor(name: String): this(TypeSpec.objectBuilder(name))
    constructor(name: ClassName): this(TypeSpec.objectBuilder(name))

    // Méthodes
    fun init(build: Code.() -> Unit) {
        builder.addInitializerBlock(Code().apply(build).spec)
    }
}