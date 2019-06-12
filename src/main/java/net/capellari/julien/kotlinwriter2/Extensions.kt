package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import net.capellari.julien.kotlinwriter2.interfaces.*
import kotlin.reflect.KClass

// Annotable
fun Annotable.annotate(type: KClass<*>) = annotate(type.asClassName())
inline fun <reified T: Annotation> Annotable.annotate() = annotate(T::class.asClassName())

// Parameter
infix fun String.of(type: TypeName)  = Parameter(this, type)
infix fun String.of(type: KClass<*>) = Parameter(this, type.asTypeName())

infix fun Parameter.default(value: Named)  = also { default(value) }
infix fun Parameter.default(value: Number) = also { default(value) }
infix fun Parameter.default(value: String) = also { default(value) }