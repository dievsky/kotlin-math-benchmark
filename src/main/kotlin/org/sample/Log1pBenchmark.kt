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
import scientifik.kmath.operations.RealField
import scientifik.kmath.structures.BufferedNDFieldElement
import scientifik.kmath.structures.NDField
import scientifik.kmath.structures.RealNDField
import java.util.concurrent.TimeUnit
import kotlin.math.ln1p
import kotlin.random.Random

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class Log1pBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    var src: DoubleArray = DoubleArray(0)
    lateinit var viktorArray: F64Array

    @Setup
    fun setup() {
        src = DoubleArray(arraySize) { RANDOM.nextDouble() }
        viktorArray = src.asF64Array()
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(viktorArray.log1p())
    }

    @Benchmark
    fun fastMath(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { FastMath.log1p(src[it]) })
    }

    @Benchmark
    fun math(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { ln1p(src[it]) })
    }

    companion object {
        val RANDOM = Random(42)
    }

}
