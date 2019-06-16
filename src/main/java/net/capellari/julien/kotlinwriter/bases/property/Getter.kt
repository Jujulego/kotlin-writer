package net.capellari.julien.kotlinwriter.bases.property

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.function.Return

class Getter: AbsCallable(FunSpec.getterBuilder()), Return