package org.sample

import org.apache.commons.math3.util.FastMath
import org.jetbrains.kotlinx.multik.api.Multik
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.exp
import kotlin.math.ln

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class LogSumExpBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(ln(Multik.math.sum(Multik.math.exp(s.multikArray1))))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1.logSumExp())
    }

    @Benchmark
    fun math(bh: Blackhole) {
        var res = 0.0
        s.src1.forEach { res += exp(it) }
        bh.consume(ln(res))
    }

    @Benchmark
    fun fastMath(bh: Blackhole) {
        var res = 0.0
        s.src1.forEach { res += FastMath.exp(it) }
        bh.consume(FastMath.log(res))
    }
}

