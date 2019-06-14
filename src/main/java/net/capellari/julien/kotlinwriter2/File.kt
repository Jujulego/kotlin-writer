package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.type.AbsContainer
import net.capellari.julien.kotlinwriter2.bases.type.AbsType

class File(pkg: String, name: String): AbsContainer(), Wrapper<FileSpec> {
    // Attributs
    val builder = FileSpec.builder(pkg, name)

    // Properties
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun add(func: AbsCallable) {
        builder.addFunction(func.spec)
    }
    override fun add(type: AbsType) {
        builder.addType(type.spec)
    }
}