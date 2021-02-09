package org.sample

import org.apache.commons.math3.util.FastMath
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.math.exp
import kotlin.math.ln

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class LogAddExpBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var s: MathArrayStructures

    @Setup
    fun setup() {
        s = MathArrayStructures(arraySize)
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(Multik.math.log(Multik.math.exp(s.multikArray1) + Multik.math.exp(s.multikArray2)))
    }

    @Benchmark
    fun kmath(bh: Blackhole) {
        bh.consume(with(s.field) { ln(add(exp(s.kmathArray1), exp(s.kmathArray2))) })
    }


    @Benchmark
    fun viktor(bh: Blackhole) {
        bh.consume(s.viktorArray1 logAddExp s.viktorArray2)
    }

    @Benchmark
    fun math(bh: Blackhole) {
        val src1 = s.src1
        val src2 = s.src2
        bh.consume(DoubleArray(arraySize) { ln(exp(src1[it]) + exp(src2[it])) })
    }

    @Benchmark
    fun fastMath(bh: Blackhole) {
        val src1 = s.src1
        val src2 = s.src2
        bh.consume(DoubleArray(arraySize) { FastMath.log(FastMath.exp(src1[it]) + FastMath.exp(src2[it])) })
    }
}