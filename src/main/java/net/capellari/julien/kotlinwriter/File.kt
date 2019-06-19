package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import net.capellari.julien.kotlinwriter.bases.Wrapper
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property
import net.capellari.julien.kotlinwriter.bases.type.AbsContainer
import net.capellari.julien.kotlinwriter.bases.type.AbsType

class File(pkg: String, name: String): AbsContainer(pkg, name), Wrapper<FileSpec> {
    // Attributs
    val builder = FileSpec.builder(pkg, name)

    // Properties
    override val spec get() = builder.build()
    override val containerName get() = pkg

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun addCallable(func: AbsCallable) {
        builder.addFunction(func.spec)
    }
    override fun addType(type: AbsType) {
        builder.addType(type.spec)
    }
    override fun addProperty(prop: Property) {
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