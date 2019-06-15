package net.capellari.julien.kotlinwriter2.bases.property

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter2.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter2.bases.function.Return

class Getter: AbsCallable(FunSpec.getterBuilder()), Return