package net.capellari.julien.kotlinwriter2.interfaces

import com.squareup.kotlinpoet.ClassName

interface Annotable {
    // Methods
    fun annotate(annotation: ClassName)
}