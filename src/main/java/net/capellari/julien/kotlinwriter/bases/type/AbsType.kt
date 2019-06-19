package net.capellari.julien.kotlinwriter.bases.type

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import net.capellari.julien.kotlinwriter.TypeParameter
import net.capellari.julien.kotlinwriter.bases.Annotable
import net.capellari.julien.kotlinwriter.bases.Modifiable
import net.capellari.julien.kotlinwriter.bases.Templatable
import net.capellari.julien.kotlinwriter.bases.Wrapper
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.property.Property

abstract class AbsType(val builder: TypeSpec.Builder, pkg: String, name: String):
        AbsContainer(pkg, name),
        Wrapper<TypeSpec>, Type, Annotable, Templatable, Modifiable {

    // Properties
    override val containerName get() = fullName
    override val spec get() = builder.build()

    // Methods
    override fun annotate(annotation: ClassName) {
        builder.addAnnotation(annotation)
    }

    override fun add(tparam: TypeParameter) {
        builder.addTypeVariable(tparam.typeName)
    }

    override fun modifier(modifier: KModifier) {
        builder.addModifiers(modifier)
    }

    fun superclass(type: TypeName, vararg params: String) {
        builder.superclass(type)

        params.forEach {
            builder.addSuperclassConstructorParameter(it)
        }
    }
    fun superinterface(type: TypeName) {
        builder.addSuperinterface(type)
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
}