package org.sample

import org.jetbrains.kotlinx.multik.ndarray.operations.times
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class MultiplicationBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(s.multikArray1 * s.multikArray2)
    }

    @Benchmark
    fun kmath(bh: Blackhole) {
        bh.consume(s.field.multiply(s.kmathArray1, s.kmathArray2))
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1 * s.viktorArray2)
    }

    @Benchmark
    fun nd4j(bh: Blackhole) {
        bh.consume(s.nd4jArray1.mul(s.nd4jArray2))
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        val src1 = s.src1
        val src2 = s.src2
        bh.consume(DoubleArray(arraySize) { src1[it] * src2[it] })
    }
}