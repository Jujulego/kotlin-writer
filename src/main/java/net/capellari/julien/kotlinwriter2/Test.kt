package net.capellari.julien.kotlinwriter2

fun test() {
    function("test", "a" of Int::class, "b" of Int::class default 0, returns = Int::class) { (a, b) ->
        + "return $a + $b"
    }
}