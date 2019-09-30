package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.*
import net.capellari.julien.kotlinwriter.bases.*
import net.capellari.julien.kotlinwriter.bases.Receiver
import net.capellari.julien.kotlinwriter.bases.function.*
import net.capellari.julien.kotlinwriter.bases.property.Property as AbsProperty
import net.capellari.julien.kotlinwriter.bases.type.AbsContainer
import net.capellari.julien.kotlinwriter.bases.type.AbsType
import net.capellari.julien.kotlinwriter.bases.type.Constructor
import net.capellari.julien.kotlinwriter.bases.type.Type
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.valueParameters

// Annotable
fun Annotable.annotate(type: KClass<*>, vararg args: Argument) = annotate(type.asClassName(), *args)
inline fun <reified T: Annotation> Annotable.annotate(vararg args: Argument) = annotate(T::class.asClassName(), *args)

// Container
fun AbsContainer.class_(name: String, build: Class.(Class) -> Unit)
        = Class(containerName, name).also {
            it.(build)(it)
            add(it)
        }

fun AbsContainer.object_(name: String, build: Object.(Object) -> Unit)
        = Object(containerName, name).also {
            it.(build)(it)
            add(it)
        }

fun AbsContainer.function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: TypeName? = null, build: AbsCallable.(List<Parameter>) -> Unit = {}): Function
        = Function(name).also { f ->
            receiver?.let { f.receiver(it) }
            returns?.let { f.returns(it) }

            f.build(f.parameters(*params))
            add(f)
        }

fun AbsContainer.function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: TypeName? = null, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns, build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: Type, returns: TypeName? = null, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.typeName, returns = returns, build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver, returns = returns.asTypeName(), build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns.asTypeName(), build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: Type, returns: KClass<*>, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.typeName, returns = returns.asTypeName(), build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: Type, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver, returns = returns.typeName, build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: KClass<*>, returns: Type, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.asTypeName(), returns = returns.typeName, build = build)
fun AbsContainer.function(name: String, vararg params: Parameter, receiver: Type, returns: Type, build: AbsCallable.(List<Parameter>) -> Unit = {})
        = function(name, *params, receiver = receiver.typeName, returns = returns.typeName, build = build)

fun AbsContainer.property(param: Parameter, receiver: TypeName? = null, build: AbsProperty.() -> Unit = {})
        = Property(param.name, param.type).also { p ->
            receiver?.let { p.receiver(it) }
            param.default?.let { p.init(it) }

            p.(build)()
            add(p)
        }
fun AbsContainer.property(param: Parameter, receiver: KClass<*>, build: AbsProperty.() -> Unit = {})
        = property(param, receiver.asTypeName(), build)
fun AbsContainer.property(param: Parameter, receiver: Type, build: AbsProperty.() -> Unit = {})
        = property(param, receiver.typeName, build)

inline fun <reified T: Templatable> AbsContainer.template(vararg tparams: TypeParameter, crossinline build: (Array<out TypeParameter>) -> T): T
        = run {
            templating {
                build(tparams).apply { tparams.forEach { add(it) } }
            }.also {
                when (it) {
                    is AbsType -> add(it)
                    is AbsProperty -> add(it)
                    is AbsCallable -> add(it)
                }
            }
        }

// AbsType
fun AbsType.superclass(pkg: String, name: String, vararg params: String)
        = superclass(ClassName(pkg, name), *params)

fun AbsType.superclass(cls: KClass<*>, vararg params: String)
        = superclass(cls.asTypeName(), *params)

inline fun <reified T: Any> AbsType.superclass(vararg params: String)
        = superclass(T::class.asTypeName(), *params)

fun AbsType.superinterface(pkg: String, name: String)
        = superinterface(ClassName(pkg, name))

fun AbsType.superinterface(cls: KClass<*>)
        = superinterface(cls.asTypeName())

inline fun <reified T: Any> AbsType.superinterface()
        = superinterface(T::class.asTypeName())

inline fun AbsType.constructor(vararg params: Parameter, primary: Boolean = false, crossinline build: Constructor.(List<Parameter>) -> Unit) {
    val c = Constructor()
    c.(build)(c.parameters(*params))

    if (primary) {
        builder.primaryConstructor(c.spec)
    } else {
        add(c)
    }
}

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

inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = function(name, *params, receiver = receiver, returns = returns) { ps ->
            modifier(KModifier.OPERATOR)
            build(ps)
        }

inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: KClass<*>, returns: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.asTypeName(), returns = returns, build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: Type, returns: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.typeName, returns = returns, build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver, returns = returns.asTypeName(), build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.asTypeName(), returns = returns.asTypeName(), build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: Type, returns: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.typeName, returns = returns.asTypeName(), build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: Type, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver, returns = returns.typeName, build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: KClass<*>, returns: Type, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.asTypeName(), returns = returns.typeName, build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: Type, returns: Type, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.typeName, returns = returns.typeName, build = build)

// File
fun createFile(pkg: String, name: String, build: File.() -> Unit)
        = File(pkg, name).apply(build)

fun createFile(cls: ClassName, build: File.() -> Unit)
        = File(cls.packageName, cls.simpleName).apply(build)

fun File.class_(build: Class.(Class) -> Unit): Class
        = class_(name, build)

fun File.import(file: File, name: String? = null, alias: String? = null)
        = import(file.pkg, name ?: file.name, alias)

fun File.import(name: ClassName, alias: String? = null)
        = import(name.packageName, name.simpleName, alias)

fun File.import(cls: KClass<*>, alias: String? = null)
        = import(cls.asClassName(), alias)

inline fun <reified T: Any> File.import(alias: String? = null)
        = import(T::class, alias)

// KClass
fun<T: Any> KClass<T>.asNullableTypeName(nullable: Boolean = true)
        = asTypeName().copy(nullable = nullable)

// Lambda
fun lambda(vararg params: Parameter, receiver: TypeName? = null, returns: TypeName = Unit::class.asTypeName())
        = Lambda(returns).also {
            it.params.addAll(params)
            it.receiver = receiver
        }

fun lambda(vararg params: Parameter, receiver: KClass<*>, returns: TypeName = Unit::class.asTypeName())
        = lambda(*params, receiver = receiver.asTypeName(), returns = returns)
fun lambda(vararg params: Parameter, receiver: Type, returns: TypeName = Unit::class.asTypeName())
        = lambda(*params, receiver = receiver.typeName, returns = returns)
fun lambda(vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>)
        = lambda(*params, receiver = receiver, returns = returns.asTypeName())
fun lambda(vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>)
        = lambda(*params, receiver = receiver.asTypeName(), returns = returns.asTypeName())
fun lambda(vararg params: Parameter, receiver: Type, returns: KClass<*>)
        = lambda(*params, receiver = receiver.typeName, returns = returns.asTypeName())
fun lambda(vararg params: Parameter, receiver: TypeName? = null, returns: Type)
        = lambda(*params, receiver = receiver, returns = returns.typeName)
fun lambda(vararg params: Parameter, receiver: KClass<*>, returns: Type)
        = lambda(*params, receiver = receiver.asTypeName(), returns = returns.typeName)
fun lambda(vararg params: Parameter, receiver: Type, returns: Type)
        = lambda(*params, receiver = receiver.typeName, returns = returns.typeName)

// Parameter
infix fun String.of(type: TypeName)  = Parameter(this, type)
infix fun String.of(type: KClass<*>) = Parameter(this, type.asTypeName())
infix fun String.of(type: Type)      = Parameter(this, type.typeName)

infix fun String.to(value: Named)  = Argument(this, value.name)
infix fun String.to(value: Number) = Argument(this, value.toString())
infix fun String.to(value: String) = Argument(this, value)

infix fun Parameter.default(value: Named)  = also { default(value) }
infix fun Parameter.default(value: Number) = also { default(value) }
infix fun Parameter.default(value: String) = also { default(value) }

fun arg(value: Named)  = Argument(null, value.name)
fun arg(value: Number) = Argument(null, value.toString())
fun arg(value: String) = Argument(null, value)

fun vararg(param: Parameter) = param.apply { modifier(KModifier.VARARG) }

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

// TypeParameter
infix fun String.bound(type: TypeName)  = TypeParameter(this).apply { bounds.add(type) }
infix fun String.bound(type: KClass<*>) = TypeParameter(this).apply { bounds.add(type.asTypeName()) }
infix fun String.bound(type: Type)      = TypeParameter(this).apply { bounds.add(type.typeName) }

fun unbounded(name: String) = TypeParameter(name)