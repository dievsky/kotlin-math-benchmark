package org.sample

import org.apache.commons.math3.util.FastMath
import org.jetbrains.bio.viktor.F64Array
import org.jetbrains.bio.viktor.asF64Array
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.Ndarray
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import scientifik.kmath.operations.RealField
import scientifik.kmath.structures.BufferedNDFieldElement
import scientifik.kmath.structures.NDField
import scientifik.kmath.structures.RealNDField
import java.util.concurrent.TimeUnit
import kotlin.math.exp
import kotlin.random.Random

@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
open class ExpBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000", "10000000")
    var arraySize: Int = 0

    lateinit var src: DoubleArray
    lateinit var multikArray: Ndarray<Double, D1>
    lateinit var field: RealNDField
    lateinit var kmathArray: BufferedNDFieldElement<Double, RealField>
    lateinit var viktorArray: F64Array

    @Setup
    fun setup() {
        src = DoubleArray(arraySize) { RANDOM.nextDouble() }
        multikArray = Multik.ndarray(src)
        field = NDField.real(arraySize)
        kmathArray = field.produce { a -> src[a[0]] }
        viktorArray = src.asF64Array()
    }

    @Benchmark
    fun multik(bh: Blackhole) {
        bh.consume(Multik.math.exp(multikArray))
    }

    @Benchmark
    fun kmath(bh: Blackhole) {
        bh.consume(field.exp(kmathArray))
    }

    @Benchmark
    fun viktor(bh: Blackhole) = bh.consume(viktorArray.exp())

    @Benchmark
    fun fastMath(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { FastMath.exp(src[it]) })
    }

    @Benchmark
    fun math(bh: Blackhole) {
        bh.consume(DoubleArray(arraySize) { exp(src[it]) })
    }

    companion object {
        val RANDOM = Random(42)
    }

}