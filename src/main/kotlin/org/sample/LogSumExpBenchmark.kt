package org.sample

import org.apache.commons.math3.util.FastMath
import org.jetbrains.bio.viktor.F64Array
import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.Ndarray
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import scientifik.kmath.structures.RealNDField
import java.util.concurrent.TimeUnit
import kotlin.math.exp
import kotlin.math.ln
import kotlin.random.Random

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class LogSumExpBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    var src: DoubleArray = DoubleArray(0)
    lateinit var multikArray: Ndarray<Double, D1>
    lateinit var field: RealNDField
    lateinit var viktorArray: F64Array

    @Setup
    fun setup() {
        src = DoubleArray(arraySize) { RANDOM.nextDouble() }
        multikArray = Multik.ndarray(src)
        viktorArray = src.asF64Array()
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(ln(Multik.math.sum(Multik.math.exp(multikArray))))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(viktorArray.logSumExp())
    }

    @Benchmark
    fun math(bh: Blackhole) {
        var res = 0.0
        src.forEach { res += exp(it) }
        bh.consume(ln(res))
    }

    @Benchmark
    fun fastMath(bh: Blackhole) {
        var res = 0.0
        src.forEach { res += FastMath.exp(it) }
        bh.consume(FastMath.log(res))
    }

    companion object {
        val RANDOM = Random(42)
    }
}
