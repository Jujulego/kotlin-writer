package net.capellari.julien.kotlinwriter.bases

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import net.capellari.julien.kotlinwriter.Argument

interface Annotable {
    // Methods
    fun annotate(annotation: AnnotationSpec)

    fun annotate(annotation: ClassName, vararg args: Argument)
            = annotate(
        AnnotationSpec.builder(annotation)
            .apply {
                args.forEach { addMember(it.spec) }
            }
            .build()
    )
}