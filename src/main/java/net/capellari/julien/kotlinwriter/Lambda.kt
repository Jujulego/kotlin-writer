package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.*
import net.capellari.julien.kotlinwriter.bases.type.AbsTypeName
import net.capellari.julien.kotlinwriter.bases.type.Type

class Lambda(val returns: TypeName = Unit::class.asTypeName()) : AbsTypeName() {
    // Attributes
    var receiver: TypeName? = null
    val params = mutableListOf<Parameter>()

    // Properties
    override val typeName: LambdaTypeName
        get() = LambdaTypeName.get(receiver, params.map { it.spec }, returnType = returns)
            .copy(
                nullable = nullable,
                annotations = annotations
            )
}