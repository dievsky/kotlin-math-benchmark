package org.sample

import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.bio.viktor.toF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.nd4j.linalg.factory.Nd4j
import space.kscience.kmath.linear.Matrix
import space.kscience.kmath.linear.transpose
import space.kscience.kmath.nd.RealNDField
import kotlin.random.Random

class MathArrayStructures(val arraySize: Int) {

    val src1 = DoubleArray(arraySize) { RANDOM.nextDouble() }
    val src2 = DoubleArray(arraySize) { RANDOM.nextDouble() }

    val multikArray1 = Multik.ndarray(src1)
    val multikArray2 = Multik.ndarray(src2)

    val field = RealNDField(intArrayOf(arraySize))
    val kmathArray1 = field.produce { a -> src1[a[0]] }
    val kmathArray2 = field.produce { a -> src2[a[0]] }

    val viktorArray1 = src1.asF64Array()
    val viktorArray2 = src2.asF64Array()

    val nd4jArray1 = Nd4j.create(src1)
    val nd4jArray2 = Nd4j.create(src2)

}

class MathMatrixStructures(val rows: Int, val cols: Int) {

    val src1 = Array(rows) { DoubleArray(cols) { RANDOM.nextDouble() } }
    val src2 = Array(rows) { DoubleArray(cols) { RANDOM.nextDouble() } }
    val src2T = Array(cols) { i -> DoubleArray(rows) { j -> src2[j][i] } }

    val multikMatrix1 = Multik.ndarray(src1.map { it.toList() })
    val multikMatrix2 = Multik.ndarray(src2.map { it.toList() })
    val multikMatrix2T = multikMatrix2.transpose()

    val kmathMatrix1 = Matrix.real(rows, cols) { r, c -> src1[r][c] }
    val kmathMatrix2 = Matrix.real(rows, cols) { r, c -> src2[r][c] }
    val kmathMatrix2T = kmathMatrix2.transpose()

    val viktorMatrix1 = src1.toF64Array()
    val viktorMatrix2 = src2.toF64Array()
    val viktorMatrix2T = src2T.toF64Array()

    val nd4jMatrix1 = Nd4j.create(src1)
    val nd4jMatrix2 = Nd4j.create(src2)
    val nd4jMatrix2T = nd4jMatrix2.transpose()

}

private val RANDOM = Random(42)