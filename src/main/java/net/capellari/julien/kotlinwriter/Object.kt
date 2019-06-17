package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.bases.Named
import net.capellari.julien.kotlinwriter.bases.type.AbsType

class Object(pkg: String, name: String): AbsType(TypeSpec.objectBuilder(name), pkg, name) {
    override val typeName = ClassName(pkg, name)
}