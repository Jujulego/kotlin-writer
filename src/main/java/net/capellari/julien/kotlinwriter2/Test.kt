package net.capellari.julien.kotlinwriter2

fun test() {
    createFile("net.capellari.julien", "Test") {
        property("d" of String::class) {
            mutable = true
            getter {
                + "return field + \"test\""
            }
            setter("v" of String::class) { v ->
                + "field = $v"
            }
        }

        class_("Test") {
            val c = property("c" of Int::class default 8)

            function("test", "a" of Int::class, "b" of Int::class default 0, returns = Int::class) { (a, b) ->
                flow("if ($a < $b)") {
                    + "return $a + $c"
                }.next("else") {
                    + "return $b + $c"
                }
            }
        }
    }
}