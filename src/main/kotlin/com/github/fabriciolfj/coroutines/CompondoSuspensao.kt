package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

class CompondoSuspensao {
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun main()  = runBlocking {
    val time = measureTime { //calcula em milisegundos, a execucao desse processo automaticamente
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("result ${one + two}")
    }

    println("done time $time")
}