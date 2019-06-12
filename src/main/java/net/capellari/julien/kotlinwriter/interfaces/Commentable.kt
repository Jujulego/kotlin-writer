package net.capellari.julien.kotlinwriter.interfaces

interface Commentable {
    // Méthodes
    // - comment
    fun comment(format: String, vararg args: Any)
}