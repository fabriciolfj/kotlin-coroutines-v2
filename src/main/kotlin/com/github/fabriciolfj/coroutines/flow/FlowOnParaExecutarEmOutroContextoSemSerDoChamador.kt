package com.github.fabriciolfj.coroutines.flow

import com.github.fabriciolfj.coroutines.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


fun simpleV6(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it in CPU-consuming way
        log("Emitting $i")
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

fun main() = runBlocking<Unit> {
    simpleV6().collect { value ->
        log("Collected $value")
    }
}