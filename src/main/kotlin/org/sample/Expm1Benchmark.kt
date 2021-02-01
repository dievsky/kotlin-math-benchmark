package org.sample

import org.apache.commons.math3.util.FastMath
import org.jetbrains.bio.viktor.F64Array
import org.jetbrains.bio.viktor.asF64Array
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.expm1
import kotlin.random.Random

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class Expm1Benchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var src: DoubleArray
    lateinit var viktorArray: F64Array

    @Setup
    fun setup() {
        src = DoubleArray(arraySize) { RANDOM.nextDouble() }
        viktorArray = src.asF64Array()
    }

    @Benchmark
    fun viktor(bh: Blackhole) = bh.consume(viktorArray.expm1())

    @Benchmark
    fun fastMath(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { FastMath.expm1(src[it]) })
    }

    @Benchmark
    fun math(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { expm1(src[it]) })
    }

    companion object {
        val RANDOM = Random(42)
    }

}