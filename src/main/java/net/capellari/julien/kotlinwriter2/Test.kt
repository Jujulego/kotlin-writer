package net.capellari.julien.kotlinwriter2

fun test() {
    val p: Parameter = "test" of Int::class default 8
    val c = Code().apply {
        + "test($p)" + "lpe;lkemz" + p + "56"
    }
}