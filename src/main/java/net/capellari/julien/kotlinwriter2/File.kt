package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.property.Property
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
    override fun add(prop: Property) {
        builder.addProperty(prop.spec)
    }

    fun import(pkg: String, name: String, alias: String? = null) {
        if (alias == null) {
            builder.addImport(pkg, name)
        } else {
            builder.addAliasedImport(MemberName(pkg, name), alias)
        }
    }
}