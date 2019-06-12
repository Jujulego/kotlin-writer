package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

// Parameter
infix fun String.of(type: TypeName)  = Parameter(this, type)
infix fun String.of(type: KClass<*>) = Parameter(this, type.asTypeName())

infix fun Parameter.default(value: Any) = also { default(value) }