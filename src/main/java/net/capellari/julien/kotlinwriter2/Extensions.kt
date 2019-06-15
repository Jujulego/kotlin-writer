package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.*
import net.capellari.julien.kotlinwriter.asNullableTypeName
import net.capellari.julien.kotlinwriter2.bases.*
import net.capellari.julien.kotlinwriter2.bases.Receiver
import net.capellari.julien.kotlinwriter2.bases.function.*
import net.capellari.julien.kotlinwriter2.bases.property.Property as AbsProperty
import net.capellari.julien.kotlinwriter2.bases.type.AbsContainer
import net.capellari.julien.kotlinwriter2.bases.type.AbsType
import net.capellari.julien.kotlinwriter2.bases.type.Type
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.valueParameters

// Annotable
fun Annotable.annotate(type: KClass<*>) = annotate(type.asClassName())
inline fun <reified T: Annotation> Annotable.annotate() = annotate(T::class.asClassName())

// Container
fun AbsContainer.class_(name: String, build: Class.() -> Unit)
        = Class(name).apply(build).also { add(it) }

fun AbsContainer.class_(name: ClassName, build: Class.() -> Unit)
        = Class(name.simpleName).apply(build).also { add(it) }

fun AbsContainer.function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: TypeName? = null, build: AbsCallable.(List<Parameter>) -> Unit = {}): Function
        = Function(name).also { f ->
            receiver?.let { f.receiver(it) }
            returns?.let { f.returns(it) }

            f.build(f.parameters(*params))
            add(f)
        }

fun AbsContainer.function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: TypeName? = null, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns, build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver, returns = returns.asTypeName(), build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns.asTypeName(), build = build)

fun AbsContainer.property(param: Parameter, receiver: TypeName? = null, build: AbsProperty.() -> Unit = {})
        = Property(param.name, param.type).also { p ->
            receiver?.let { p.receiver(it) }
            param.default?.let { p.init(it) }

            p.(build)()
            add(p)
        }

// AbsType
inline fun <reified R: Any> AbsType.override(func: KFunction<R>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = Function(func.name).also { f ->
            f.modifier(KModifier.OVERRIDE)
            if (func.isInfix) f.modifier(KModifier.INFIX)

            func.extensionReceiverParameter?.let {
                f.receiver(it.type.asTypeName())
            }

            val params = func.valueParameters.map {
                val name = it.name
                val type = it.type.asTypeName()

                f.parameter((name ?: "") of type) {
                    if (it.isVararg) modifier(KModifier.VARARG)
                }
            }

            f.returns(func.returnType.asTypeName())
            f.(build)(params)

            add(f)
        }

// File
fun createFile(pkg: String, name: String, build: File.() -> Unit)
        = File(pkg, name).apply(build).spec

fun createFile(cls: ClassName, build: File.() -> Unit)
        = File(cls.packageName, cls.simpleName).apply(build).spec

fun File.import(name: ClassName, alias: String? = null)
        = import(name.packageName, name.simpleName, alias)

fun File.import(cls: KClass<*>, alias: String? = null)
        = import(cls.asClassName(), alias)

inline fun <reified T: Any> File.import(alias: String? = null)
        = import(T::class, alias)

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

// Type
fun Type.superclass(pkg: String, name: String, vararg params: String)
        = superclass(ClassName(pkg, name), *params)

fun Type.superclass(cls: KClass<*>, vararg params: String)
        = superclass(cls.asTypeName(), *params)

inline fun <reified T: Any> Type.superclass(vararg params: String)
        = superclass(T::class.asTypeName(), *params)

fun Type.superinterface(pkg: String, name: String)
        = superinterface(ClassName(pkg, name))

fun Type.superinterface(cls: KClass<*>)
        = superinterface(cls.asTypeName())

inline fun <reified T: Any> Type.superinterface()
        = superinterface(T::class.asTypeName())


