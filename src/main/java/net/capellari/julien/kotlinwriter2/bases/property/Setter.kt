package net.capellari.julien.kotlinwriter2.bases.property

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.function.Parameters
import net.capellari.julien.kotlinwriter2.bases.function.Return

class Setter: AbsCallable(FunSpec.setterBuilder()), Parameters