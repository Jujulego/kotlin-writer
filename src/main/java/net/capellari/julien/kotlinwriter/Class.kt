package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.type.AbsType

class Class(pkg: String, name: String) : AbsType(TypeSpec.classBuilder(name), pkg, name) {
    // Attributes
    override val typeName = ClassName(pkg, name)

    // Methods
    override fun toString() = name

    fun companion(build: Companion.() -> Unit)
            = Companion(pkg, name).also {
                it.build()
                builder.addType(it.spec)
            }

    // Classes
    class Companion internal constructor(pkg: String, name: String): AbsType(TypeSpec.companionObjectBuilder(), pkg, name) {
        override val typeName = ClassName(pkg, name)
    }
}