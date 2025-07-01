package com.github.fabriciolfj.coroutines.coroutinescomretorno

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

private var zeroTime = System.currentTimeMillis()

fun log(message: Any?) =
    println("${System.currentTimeMillis() - zeroTime} " +
            "[${Thread.currentThread().name}] $message")

suspend fun slowlyAddNumbers(a: Int, b: Int): Int {
    log("Waiting a bit before calculating $a + $b")
    delay(100.milliseconds * a)
    return a + b
}


fun main() = runBlocking {
    log("Starting the async computation")
    val myFirstDeferred = async { slowlyAddNumbers(2, 2) }
    val mySecondDeferred = async { slowlyAddNumbers(4, 4) }
    log("Waiting for the deferred value to be available")
    log("The first result: ${myFirstDeferred.await()}")
    log("The second result: ${mySecondDeferred.await()}")
}
