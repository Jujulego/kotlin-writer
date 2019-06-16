package net.capellari.julien.kotlinwriter.bases

import com.squareup.kotlinpoet.ClassName

interface Annotable {
    // Methods
    fun annotate(annotation: ClassName)
}