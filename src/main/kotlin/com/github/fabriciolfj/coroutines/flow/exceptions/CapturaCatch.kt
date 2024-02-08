package com.github.fabriciolfj.coroutines.flow.exceptions

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class CapturaCatch {
}

fun simpleV2() = flow {
    for(i in 1 ..3) {
        println("emitting $i")
        emit(i)
    }
}.map {
    check(it <= 1) { "valor invalido" }
    "string $it"
}


fun main() = runBlocking {
    simpleV2()
        .catch { e -> println("error $e") }
        .collect{ value -> println(value) }
}