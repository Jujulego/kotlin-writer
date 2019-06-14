package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import net.capellari.julien.kotlinwriter.asNullableTypeName
import net.capellari.julien.kotlinwriter2.bases.*
import net.capellari.julien.kotlinwriter2.bases.function.Callable
import net.capellari.julien.kotlinwriter2.bases.function.Parameters
import net.capellari.julien.kotlinwriter2.bases.function.Receiver
import net.capellari.julien.kotlinwriter2.bases.function.Return
import kotlin.reflect.KClass

// Annotable
fun Annotable.annotate(type: KClass<*>) = annotate(type.asClassName())
inline fun <reified T: Annotation> Annotable.annotate() = annotate(T::class.asClassName())

// Function
fun function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: TypeName? = null, build: Callable.(List<Parameter>) -> Unit = {}): Function {
    val f = Function(name)

    receiver?.let { f.receiver(it) }
    returns?.let { f.returns(it) }

    val p = f.parameters(*params)
    f.build(p)

    return f
}
fun function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: TypeName? = null, build: Callable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns, build = build)
fun function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>, build: Callable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver, returns = returns.asTypeName(), build = build)
fun function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>, build: Callable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns.asTypeName(), build = build)

// Parameter
infix fun String.of(type: TypeName)  = Parameter(this, type)
infix fun String.of(type: KClass<*>) = Parameter(this, type.asTypeName())

infix fun Parameter.default(value: Named)  = also { default(value) }
infix fun Parameter.default(value: Number) = also { default(value) }
infix fun Parameter.default(value: String) = also { default(value) }

// Parameters
inline fun <reified T: Any> Parameters.parameter(name: String, default: Named? = null, nullable: Boolean = false, crossinline build: Parameter.() -> Unit = {})
        = parameter(name of T::class.asNullableTypeName(nullable)) { default?.let { default(it) } }.also(build)

inline fun <reified T: Any> Parameters.parameter(name: String, default: Number? = null, nullable: Boolean = false, crossinline build: Parameter.() -> Unit = {})
        = parameter(name of T::class.asNullableTypeName(nullable)) { default?.let { default(it) } }.also(build)

inline fun <reified T: Any> Parameters.parameter(name: String, default: String? = null, nullable: Boolean = false, crossinline build: Parameter.() -> Unit = {})
        = parameter(name of T::class.asNullableTypeName(nullable)) { default?.let { default(it) } }.also(build)

// Return
fun Return.returns(type: KClass<*>, nullable: Boolean = false) = returns(type.asNullableTypeName(nullable))
inline fun <reified T: Any> Return.returns(nullable: Boolean = false) = returns(T::class.asNullableTypeName(nullable))

// Receiver
fun Receiver.receiver(type: KClass<*>, nullable: Boolean = false) = receiver(type.asNullableTypeName(nullable))
inline fun <reified T: Any> Receiver.receiver(nullable: Boolean = false) = receiver(T::class.asNullableTypeName(nullable))