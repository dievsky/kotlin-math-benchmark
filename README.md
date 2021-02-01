# Benchmarking Kotlin mathematical libraries

The goal of this project is to compare the performance of several mathematical libraries
for Kotlin.

## Libraries

[kmath](https://github.com/mipt-npm/kmath), version 0.2.0-dev-5

[multik](https://github.com/Kotlin/multik), version 0.0.1-dev-11

[viktor](https://github.com/JetBrains-Research/viktor), version 1.0.2

## Benchmarks

We test the performance of the following operations:
* elementwise addition and multiplication of two double arrays
* dot product (aka scalar product) of two double arrays
* sum of all elements of a double array
* elementwise exponent and logarithm of a double array

We also test the performance of the "naive" Kotlin implementations of these operations.

## Running

This is a JMH project. Its intended execution mode is
```shell
mvn clean verify
java -jar target/benchmarks.jar
```

The usual JMH options can be added to the latter command.
