package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.property.Getter
import net.capellari.julien.kotlinwriter2.bases.property.Property
import net.capellari.julien.kotlinwriter2.bases.property.Receiver
import net.capellari.julien.kotlinwriter2.bases.property.Setter

class Property(override val name: String, val type: TypeName): Property, Receiver {
    // Attributes
    override val builder = PropertySpec.builder(name, type)

    // Properties
    override val spec get() = builder.build()
    override var mutable = false
        set(m) { field = m
            builder.mutable(m)
        }

    // Methods
    override fun toString() = name

    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    override fun init(code: String) {
        builder.initializer(code)
    }

    override fun getter(build: AbsCallable.() -> Unit) {
        val g = Getter()
        g.(build)()

        builder.getter(g.spec)
    }
    override fun setter(parameter: Parameter, build: AbsCallable.(Parameter) -> Unit) {
        val s = Setter()
        val p = s.parameter(parameter)
        s.(build)(p)

        builder.getter(s.spec)
    }
}