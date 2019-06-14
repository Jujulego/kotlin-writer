package net.capellari.julien.kotlinwriter2

fun test() {
    createFile("net.capellari.julien", "Test") {
        _class("Test") {
            function("test", "a" of Int::class, "b" of Int::class default 0, returns = Int::class) { (a, b) ->
                flow("if ($a < $b)") {
                    + "return $a + $b"
                }.next("else") {
                    + "return $b + $a"
                }
            }
        }
    }
}