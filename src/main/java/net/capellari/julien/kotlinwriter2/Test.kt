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

            // Constructors
            constructor("handle" of Long::class, primary = true) {
                modifier(KModifier.INTERNAL)
            }

            constructor {
                this_("create(${(0 until 2).joinToString(", ") { "0" }})")
            }

            constructor(*coords) {
                this_("create(${coords.joinToString(", ")}")
            }

            constructor("factors" of numberArray) { (factors) ->
                this_("createA($factors)")
            }

            constructor("pt" of clsName) { (pt) ->
                this_("createC($pt)")
            }

            // Native methods
            val getDataA = function("getDataA", returns = numberArray) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            val getCoord = function("getCoord", "i" of Int::class, returns = number) {
                modifier(KModifier.PRIVATE, KModifier.EXTERNAL)
            }

            val setCoord = function("setCoord", "i" of Int::class, "v" of number) {
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

            // Operators
            get("i" of Int::class, returns = number) { (i) ->
                + "return $getCoord($i)"
            }
            set("i" of Int::class, "v" of number) { (i, v) ->
                + "return $setCoord($i, $v)"
            }

            unaryPlus(returns = clsName) {
                + "return $clsName(this)"
            }
            unaryMinus(returns = clsName) {
                + "return $clsName(${(0 until 2).joinToString(", ") { "-this[$it]" }})"
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
            + "return $clsName(${params.joinToString(", ")})"
        }
    }
}