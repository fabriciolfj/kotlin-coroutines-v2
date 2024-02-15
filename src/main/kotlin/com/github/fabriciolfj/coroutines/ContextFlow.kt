package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun logV4(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun simpleV5(): Flow<Int> = flow {
    logV4("Started simple flow")
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    simpleV5().collect { value -> log("Collected $value") }
}