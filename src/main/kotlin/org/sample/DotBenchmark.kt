package org.sample

import org.jetbrains.kotlinx.multik.default.DefaultLinAlg
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class DotBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(DefaultLinAlg.dot(s.multikArray1, s.multikArray2))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1.dot(s.viktorArray2))
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        var res = 0.0
        val src1 = s.src1
        val src2 = s.src2
        for (i in 0 until arraySize) {
            res += src1[i] * src2[i]
        }
        bh.consume(res)
    }
}