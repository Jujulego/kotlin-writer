package net.capellari.julien.kotlinwriter.bases.property

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter.bases.function.AbsCallable
import net.capellari.julien.kotlinwriter.bases.function.Parameters

class Setter: AbsCallable(FunSpec.setterBuilder()), Parameters