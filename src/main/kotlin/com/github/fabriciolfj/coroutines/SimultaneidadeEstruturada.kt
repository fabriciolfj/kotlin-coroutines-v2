package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.time.measureTime

class SimultaneidadeEstruturada {
}

suspend fun concurrentSum() : Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

suspend fun main() {
    val time = measureTime {
        println("response are ${concurrentSum()}")
    }

    println("time done $time")
}