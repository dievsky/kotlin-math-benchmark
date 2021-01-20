package org.sample

import org.jetbrains.bio.viktor.F64Array
import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.default.DefaultLinAlg
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.Ndarray
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class DotBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    private var src1: DoubleArray = DoubleArray(0)
    private var src2: DoubleArray = DoubleArray(0)
    lateinit var multikArray1: Ndarray<Double, D1>
    lateinit var multikArray2: Ndarray<Double, D1>
    lateinit var viktorArray1: F64Array
    lateinit var viktorArray2: F64Array

    @Setup
    fun setup() {
        src1 = DoubleArray(arraySize) { RANDOM.nextDouble() }
        src2 = DoubleArray(arraySize) { RANDOM.nextDouble() }
        multikArray1 = Multik.ndarray(src1)
        multikArray2 = Multik.ndarray(src2)
        viktorArray1 = src1.asF64Array()
        viktorArray2 = src2.asF64Array()
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(DefaultLinAlg.dot(multikArray1, multikArray2))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(viktorArray1.dot(viktorArray2))
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        var res = 0.0
        for (i in 0 until arraySize) {
            res += src1[i] * src2[i]
        }
        bh.consume(res)
    }

    companion object {
        val RANDOM = Random(42)
    }

}