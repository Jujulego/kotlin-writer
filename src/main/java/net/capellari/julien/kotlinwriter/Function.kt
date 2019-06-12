package net.capellari.julien.kotlinwriter

import com.squareup.kotlinpoet.FunSpec
import net.capellari.julien.kotlinwriter.function.AbsFunction
import net.capellari.julien.kotlinwriter.function.Parameters
import net.capellari.julien.kotlinwriter.function.Receiver
import net.capellari.julien.kotlinwriter.function.Returns

class Function(name: String): AbsFunction(FunSpec.builder(name)), Receiver, Parameters, Returns