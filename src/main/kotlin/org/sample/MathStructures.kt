package org.sample

import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.bio.viktor.toF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import scientifik.kmath.structures.NDField
import kotlin.random.Random

class MathArrayStructures(val arraySize: Int) {

    val src1 = DoubleArray(arraySize) { RANDOM.nextDouble() }
    val src2 = DoubleArray(arraySize) { RANDOM.nextDouble() }

    val multikArray1 = Multik.ndarray(src1)
    val multikArray2 = Multik.ndarray(src2)

    val field = NDField.real(arraySize)
    val kmathArray1 = field.produce { a -> src1[a[0]] }
    val kmathArray2 = field.produce { a -> src2[a[0]] }

    val viktorArray1 = src1.asF64Array()
    val viktorArray2 = src2.asF64Array()

}

class MathMatrixStructures(val rows: Int, val cols: Int) {

    val src1 = Array(rows) { DoubleArray(cols) { RANDOM.nextDouble() } }
    val src2 = Array(rows) { DoubleArray(cols) { RANDOM.nextDouble() } }
    val src2T = Array(cols) { i -> DoubleArray(rows) { j -> src2[j][i] } }

    val multikMatrix1 = Multik.ndarray(src1.map { it.toList() })
    val multikMatrix2 = Multik.ndarray(src2.map { it.toList() })
    val multikMatrix2T = multikMatrix2.transpose()

    val matrixField = NDField.real(rows, cols)
    val matrixFieldT = NDField.real(cols, rows)
    val matrixFieldP = NDField.real(rows, rows)
    val kmathMatrix1 = matrixField.produce { a -> src1[a[0]][a[1]] }
    val kmathMatrix2 = matrixField.produce { a -> src2[a[0]][a[1]] }
    val kmathMatrix2T = matrixFieldT.produce { a -> src2T[a[0]][a[1]] }

    val viktorMatrix1 = src1.toF64Array()
    val viktorMatrix2 = src2.toF64Array()
    val viktorMatrix2T = src2T.toF64Array()

}

private val RANDOM = Random(42)