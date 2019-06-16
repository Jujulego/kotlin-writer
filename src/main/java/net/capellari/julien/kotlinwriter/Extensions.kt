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
            modifier(KModifier.OVERRIDE, KModifier.OPERATOR)
            build(ps)
        }

inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: KClass<*>, returns: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.asTypeName(), returns = returns, build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: TypeName? = null, returns: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver, returns = returns.asTypeName(), build = build)
inline fun AbsType.operator(name: String, vararg params: Parameter, receiver: KClass<*>, returns: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator(name, *params, receiver = receiver.asTypeName(), returns = returns.asTypeName(), build = build)

inline fun AbsType.not(returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("not", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.not(returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("not", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.not(returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("not", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.not(returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("not", receiver = receiver, returns = returns, build = { build() })

inline fun AbsType.unaryPlus(returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryPlus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryPlus(returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryPlus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryPlus(returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryPlus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryPlus(returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryPlus", receiver = receiver, returns = returns, build = { build() })

inline fun AbsType.unaryMinus(returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryMinus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryMinus(returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryMinus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryMinus(returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryMinus", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.unaryMinus(returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("unaryMinus", receiver = receiver, returns = returns, build = { build() })

inline fun AbsType.inc(returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("inc", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.inc(returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("inc", receiver = receiver, returns = returns, build = { build() })

inline fun AbsType.dec(returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.() -> Unit)
        = operator("dec", receiver = receiver, returns = returns, build = { build() })
inline fun AbsType.dec(returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.() -> Unit)
        = operator("dec", receiver = receiver, returns = returns, build = { build() })

inline fun AbsType.plus(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.plus(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.plus(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.plus(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plus", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.minus(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.minus(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.minus(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minus", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.minus(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minus", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.times(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("times", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.times(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("times", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.times(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("times", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.times(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("times", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.div(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("div", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.div(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("div", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.div(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("div", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.div(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("div", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.rem(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rem", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rem(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rem", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rem(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rem", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rem(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rem", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.rangeTo(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rangeTo", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rangeTo(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rangeTo", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rangeTo(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rangeTo", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.rangeTo(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("rangeTo", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.contains(param: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("contains", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.contains(param: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("contains", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.contains(param: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("contains", param, receiver = receiver, returns = returns, build = build)
inline fun AbsType.contains(param: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("contains", param, receiver = receiver, returns = returns, build = build)

inline fun AbsType.get(vararg params: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("get", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.get(vararg params: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("get", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.get(vararg params: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("get", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.get(vararg params: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("get", *params, receiver = receiver, returns = returns, build = build)

inline fun AbsType.set(vararg params: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("set", *params, receiver = receiver, build = build)
inline fun AbsType.set(vararg params: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("set", *params, receiver = receiver, build = build)

inline fun AbsType.invoke(vararg params: Parameter, returns: TypeName, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("invoke", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.invoke(vararg params: Parameter, returns: KClass<*>, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("invoke", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.invoke(vararg params: Parameter, returns: TypeName, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("invoke", *params, receiver = receiver, returns = returns, build = build)
inline fun AbsType.invoke(vararg params: Parameter, returns: KClass<*>, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("invoke", *params, receiver = receiver, returns = returns, build = build)

inline fun AbsType.plusAssign(param: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plusAssign", param, receiver = receiver, build = build)
inline fun AbsType.plusAssign(param: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("plusAssign", param, receiver = receiver, build = build)

inline fun AbsType.minusAssign(param: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minusAssign", param, receiver = receiver, build = build)
inline fun AbsType.minusAssign(param: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("minusAssign", param, receiver = receiver, build = build)

inline fun AbsType.timesAssign(param: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("timesAssign", param, receiver = receiver, build = build)
inline fun AbsType.timesAssign(param: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("timesAssign", param, receiver = receiver, build = build)

inline fun AbsType.divAssign(param: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("divAssign", param, receiver = receiver, build = build)
inline fun AbsType.divAssign(param: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("divAssign", param, receiver = receiver, build = build)

inline fun AbsType.remAssign(param: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("remAssign", param, receiver = receiver, build = build)
inline fun AbsType.remAssign(param: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("remAssign", param, receiver = receiver, build = build)

inline fun AbsType.compareTo(vararg params: Parameter, receiver: TypeName? = null, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("compareTo", *params, receiver = receiver, returns = Int::class, build = build)
inline fun AbsType.compareTo(vararg params: Parameter, receiver: KClass<*>, crossinline build: AbsCallable.(List<Parameter>) -> Unit)
        = operator("compareTo", *params, receiver = receiver, returns = Int::class, build = build)

// File
fun createFile(pkg: String, name: String, build: File.() -> Unit)
        = File(pkg, name).apply(build)

fun createFile(cls: ClassName, build: File.() -> Unit)
        = File(cls.packageName, cls.simpleName).apply(build)

fun File.import(name: ClassName, alias: String? = null)
        = import(name.packageName, name.simpleName, alias)

fun File.import(cls: KClass<*>, alias: String? = null)
        = import(cls.asClassName(), alias)

inline fun <reified T: Any> File.import(alias: String? = null)
        = import(T::class, alias)

// KClass
fun<T: Any> KClass<T>.asNullableTypeName(nullable: Boolean = true)
        = asTypeName().copy(nullable = nullable)

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


