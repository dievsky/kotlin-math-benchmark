package org.sample

import org.jetbrains.bio.viktor.F64Array
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class MultiplicationOffsetBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathStructures
    lateinit var viktor2DArray1: F64Array
    lateinit var viktor2DArray2: F64Array

    var iteration = 0

    @Setup
    fun setup() {
        s = MathStructures(HUGE_ARRAY_SIZE)
        viktor2DArray1 = s.viktorArray1.reshape(HUGE_ARRAY_SIZE / arraySize, arraySize)
        viktor2DArray2 = s.viktorArray2.reshape(HUGE_ARRAY_SIZE / arraySize, arraySize)
    }

    @Setup(Level.Invocation)
    fun inc() {
        iteration++
        if ((iteration + 1) * arraySize >= HUGE_ARRAY_SIZE) {
            iteration = 0
        }
    }

    @Benchmark
    fun viktor(bh: Blackhole) {
        val viktorArray1 = viktor2DArray1.V[iteration]
        val viktorArray2 = viktor2DArray2.V[iteration]
        bh.consume(viktorArray1 * viktorArray2)
    }

    @Benchmark
    fun loop(bh: Blackhole) {
        val src1 = s.src1
        val src2 = s.src2
        val offset = iteration * arraySize
        bh.consume(DoubleArray(arraySize) { src1[it + offset] * src2[it + offset] })
    }

    companion object {
        const val HUGE_ARRAY_SIZE = 10_000_000
    }
}