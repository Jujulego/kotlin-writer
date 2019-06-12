package net.capellari.julien.kotlinwriter.interfaces

interface Wrapper<out S,out B> {
    // Attributs
    val builder: B
    val spec: S
}