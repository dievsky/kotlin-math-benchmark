package org.sample

import org.apache.commons.math3.util.FastMath
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.ln1p

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class Log1pBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1.log1p())
    }

    @Benchmark
    fun fastMath(bh: Blackhole) {
        val src = s.src1
        bh.consume(DoubleArray(arraySize) { FastMath.log1p(src[it]) })
    }

    @Benchmark
    fun math(bh: Blackhole) {
        val src = s.src1
        bh.consume(DoubleArray(arraySize) { ln1p(src[it]) })
    }
}
