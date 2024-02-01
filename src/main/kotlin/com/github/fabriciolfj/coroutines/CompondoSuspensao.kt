package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

class CompondoSuspensao {
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

suspend fun doSomethingUsefulOne(): Int {
    log("doSomethingUsefulOne")
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    log("doSomethingUsefulTwo")
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