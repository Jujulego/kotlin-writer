package net.capellari.julien.kotlinwriter.bases.type

import com.squareup.kotlinpoet.ClassName
import net.capellari.julien.kotlinwriter.bases.Annotable

abstract class AbsTypeName : Type, Annotable {
    // Attributes
    var nullable = false

    protected val annotations = mutableListOf<ClassName>()

    // Methods
    override fun annotate(annotation: ClassName) {
        annotations.add(annotation)
    }
}