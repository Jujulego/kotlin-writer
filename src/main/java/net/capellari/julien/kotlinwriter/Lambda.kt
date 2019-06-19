package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import net.capellari.julien.kotlinwriter.bases.type.Type

class Lambda(val returns: TypeName = Unit::class.asTypeName()) : Type {
    // Attributes
    var receiver: TypeName? = null
    val params = mutableListOf<Parameter>()

    // Properties
    override val typeName: LambdaTypeName
        get() = LambdaTypeName.get(receiver, params.map { it.spec }, returnType = returns)
}