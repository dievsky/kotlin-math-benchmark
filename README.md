# Benchmarking Kotlin mathematical libraries

The goal of this project is to compare the performance of several mathematical libraries
for Kotlin.

## Libraries

[kmath](https://github.com/mipt-npm/kmath)

[multik](https://github.com/Kotlin/multik)

[viktor](https://github.com/JetBrains-Research/viktor)

## Benchmarks

We test the performance of the following operations:
* elementwise addition and multiplication of two double arrays, both with and without an offset
* dot product (aka scalar product) of two double arrays
* sum of all elements of a double array
* elementwise exponent, logarithm, `expm1` and `log1p` of a double array

We also test the performance of the "naive" Kotlin implementations of these operations.

## Running

This is a JMH project. Its intended execution mode is
```shell
mvn clean verify
java -jar target/benchmarks.jar
```

The usual JMH options can be added to the latter command.
