package org.sample

import org.jetbrains.bio.viktor.F64Array
import org.jetbrains.bio.viktor._I
import org.jetbrains.kotlinx.multik.api.Multik
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import space.kscience.kmath.linear.RealMatrixContext
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class MatrixDotBenchmark {

    @Param("100", "1000")
    var rows: Int = 0

    @Param("100", "1000")
    var cols: Int = 0

    lateinit var s: MathMatrixStructures

    @Setup
    fun setup() {
        s = MathMatrixStructures(rows, cols)
    }

    @Benchmark
    fun kmath(bh: Blackhole) {
        with(RealMatrixContext) {
            bh.consume(s.kmathMatrix1 dot s.kmathMatrix2T)
        }
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(Multik.linalg.dot(s.multikMatrix1, s.multikMatrix2T))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        val viktorMatrix1 = s.viktorMatrix1
        val viktorMatrix2T = s.viktorMatrix2T
        bh.consume(F64Array(rows, rows) { i, k -> viktorMatrix1.V[i].dot(viktorMatrix2T.V[_I, k]) })
    }

    @Benchmark
    fun nd4j(bh: Blackhole) {
        bh.consume(s.nd4jMatrix1.mmul(s.nd4jMatrix2T))
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        val src1 = s.src1
        val src2T = s.src2T
        bh.consume(Array(rows) { i ->
            DoubleArray(rows) { k ->
                var res = 0.0
                for (j in 0 until cols) {
                    res += src1[i][j] * src2T[j][k]
                }
                res
            }
        })
    }
}