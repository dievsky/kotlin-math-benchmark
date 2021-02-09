package org.sample

import org.jetbrains.kotlinx.multik.api.Multik
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class SumBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(Multik.math.sum(s.multikArray1))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1.sum())
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        var res = 0.0
        s.src1.forEach { res += it }
        bh.consume(res)
    }
}

