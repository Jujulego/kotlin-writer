package net.capellari.julien.kotlinwriter2

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier

fun test() {
    val clsName = ClassName("net.capellari.julien", "Point")

    val number = Int::class
    val numberArray = IntArray::class

    val coords = arrayOf("x" of number, "y" of number)

    createFile(clsName) {
        // Classes
        class_(clsName) {
            // Inheritance
            superclass("net.capellari.julien.threed.jni", "JNIClass", "handle")

            // Companion
            companion {
                function("create", *coords, returns = Long::class) {
                    annotate<JvmStatic>()
                    modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
                }

                function("createA", "factors" of numberArray, returns = Long::class) {
                    annotate<JvmStatic>()
                    modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
                }

                function("createC", "other" of clsName, returns = Long::class) {
                    annotate<JvmStatic>()
                    modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
                }
            }

            // Native methods
            val getDataA = function("getDataA", returns = numberArray) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            function("getCoord", "i" of Int::class, returns = number) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            function("setCoord", "i" of Int::class, "v" of number) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            val equal = function("equal", "other" of clsName, returns = Boolean::class) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            // Properties
            val data = property("data" of numberArray) {
                getter {
                    + "returns $getDataA()"
                }
            }

            // Methods
            override(Any::equals) { (other) ->
                flow("if ($other === this)") {
                    + "return true"
                }

                flow("if (other is $clsName)") {
                    + "return $equal($other)"
                }

                + "return super.equals(other)"
            }

            override(Any::hashCode) {
                + "return $data.contentHashCode()"
            }

            override(Any::toString) {
                + "return \"Point(${(0 until 2).joinToString(", ") { "\${this[$it]}" }})\""
            }
        }

        // Utils
        function("point", *coords, returns = clsName) { params ->
            + "return $clsName(${params.joinToString(", ") { it.name }})"
        }
    }
}