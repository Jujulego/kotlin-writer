package net.capellari.julien.kotlinwriter.interfaces

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import net.capellari.julien.kotlinwriter.Function
import net.capellari.julien.kotlinwriter.Property

interface Container {
    // Functions
    fun function(name: String, build: Function.() -> Unit): FunSpec

    // Propriétés
    fun property(name: String, type: TypeName, build: Property.() -> Unit = {}): PropertySpec
}