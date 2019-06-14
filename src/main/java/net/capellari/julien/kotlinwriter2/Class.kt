package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter2.bases.Named
import net.capellari.julien.kotlinwriter2.bases.Wrapper
import net.capellari.julien.kotlinwriter2.bases.type.AbsType

class Class(override val name: String) : AbsType(TypeSpec.classBuilder(name)), Wrapper<TypeSpec>, Named