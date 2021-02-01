package org.sample

import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import scientifik.kmath.structures.NDField
import kotlin.random.Random

class MathStructures(val arraySize: Int) {

    val src1 = DoubleArray(arraySize) { RANDOM.nextDouble() }
    val src2 = DoubleArray(arraySize) { RANDOM.nextDouble() }
    val multikArray1 = Multik.ndarray(src1)
    val multikArray2 = Multik.ndarray(src2)
    val field = NDField.real(arraySize)
    val kmathArray1 = field.produce { a -> src1[a[0]] }
    val kmathArray2 = field.produce { a -> src2[a[0]] }
    val viktorArray1 = src1.asF64Array()
    val viktorArray2 = src2.asF64Array()

    companion object {
        val RANDOM = Random(42)
    }

}