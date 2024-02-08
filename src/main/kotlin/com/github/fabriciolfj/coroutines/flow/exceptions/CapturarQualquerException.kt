package com.github.fabriciolfj.coroutines.flow.exceptions

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class CapturarQualquerException {
}

fun simpleV3(): Flow<Int> = flow {
    for (i in 1 .. 3) {
        println("emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    simpleV3().onEach { value ->
        check(value <= 1) {"collected $value"}
        println(value)
    }.catch { e -> println("error $e") }
        .collect()
}