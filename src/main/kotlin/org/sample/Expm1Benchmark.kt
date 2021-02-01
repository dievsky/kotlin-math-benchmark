package org.sample

import org.apache.commons.math3.util.FastMath
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.expm1

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class Expm1Benchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathStructures

    @Setup
    fun setup() {
        s = MathStructures(arraySize)
    }

    @Benchmark
    fun viktor(bh: Blackhole) = bh.consume(s.viktorArray1.expm1())

    @Benchmark
    fun fastMath(bh: Blackhole) {
        val src = s.src1
        bh.consume(DoubleArray(arraySize) { FastMath.expm1(src[it]) })
    }

    @Benchmark
    fun math(bh: Blackhole) {
        val src = s.src1
        bh.consume(DoubleArray(arraySize) { expm1(src[it]) })
    }
}