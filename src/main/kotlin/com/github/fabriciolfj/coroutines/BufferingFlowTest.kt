package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun simpleV7() = flow {
    for (k in 1..3) {
        delay(100)
        emit(k)
    }
}


fun main() = runBlocking {
    val time = measureTimeMillis {
        simpleV7()
            .buffer()
            .collect {
                delay(300)
                println(it)
            }
    }

    println("time $time")
}