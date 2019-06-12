package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.AbsType

class Class: AbsType {
    // Constructors
    constructor(name: ClassName): super(TypeSpec.classBuilder(name))
    constructor(name: String): super(TypeSpec.classBuilder(name))
    constructor(): super(TypeSpec.anonymousClassBuilder())

    // Methods
    // - constructors
    fun primaryConstructor(build: Constructor.() -> Unit) {
        builder.primaryConstructor(Constructor().apply(build).spec)
    }

    fun constructor(build: Constructor.() -> Unit) {
        builder.addFunction(Constructor().apply(build).spec)
    }

    fun companion(name: String? = null, build: Companion.() -> Unit) {
        builder.addType(Companion(name).apply(build).spec)
    }

    fun init(build: Code.() -> Unit) {
        builder.addInitializerBlock(Code().apply(build).spec)
    }

    // Classe
    class Companion(name: String? = null): Object(TypeSpec.companionObjectBuilder(name))
}