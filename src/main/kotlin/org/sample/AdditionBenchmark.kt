package org.sample

import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class AdditionBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathStructures

    @Setup
    fun setup() {
        s = MathStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(s.multikArray1 + s.multikArray2)
    }

    @Benchmark
    fun kmath(bh: Blackhole) {
        bh.consume(s.field.add(s.kmathArray1, s.kmathArray2))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1 + s.viktorArray2)
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        val src1 = s.src1
        val src2 = s.src2
        bh.consume(DoubleArray(arraySize) { src1[it] + src2[it] })
    }
}