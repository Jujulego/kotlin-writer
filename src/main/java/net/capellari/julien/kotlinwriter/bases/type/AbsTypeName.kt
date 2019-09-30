package net.capellari.julien.kotlinwriter.bases.type

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import net.capellari.julien.kotlinwriter.bases.Annotable

abstract class AbsTypeName : Type, Annotable {
    // Attributes
    var nullable = false

    protected val annotations = mutableListOf<AnnotationSpec>()

    // Methods
    override fun annotate(annotation: AnnotationSpec) {
        annotations.add(annotation)
    }
}