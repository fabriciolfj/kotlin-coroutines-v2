package com.github.fabriciolfj.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class FluxoFlatMapConcat {
}


fun request(value: Int) = flow {
    emit("$value -> First")
    delay(500)
    emit("$value -> Second")
    delay(500)
    emit("$value -> third")

}

fun main() = runBlocking {
    val numbers = (1 .. 3).asFlow().onEach { delay(100) }
    val start = System.currentTimeMillis()

    /*
    numbers.flatMapConcat { request(it) }
        .collect {value ->
            println("$value at ${System.currentTimeMillis() - start} ms from start")
        }
     */
    /*
    numbers.flatMapMerge { request(it) }
        .collect {value ->
            println("$value at ${System.currentTimeMillis() - start} ms from start")
        }
    */

    numbers.flatMapLatest { request(it) }
        .collect {value ->
            println("$value at ${System.currentTimeMillis() - start} ms from start")
        }
}