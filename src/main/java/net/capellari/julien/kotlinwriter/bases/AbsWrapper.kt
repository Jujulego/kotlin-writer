package net.capellari.julien.kotlinwriter.bases

import net.capellari.julien.kotlinwriter.interfaces.Wrapper

abstract class AbsWrapper<S,B>(override val builder: B) : Wrapper<S,B>