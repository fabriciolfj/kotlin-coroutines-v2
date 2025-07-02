package com.github.fabriciolfj.coroutines.examplescope

import com.github.fabriciolfj.coroutines.log
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class Simulacao {
}

suspend fun generateValue() : Int {
    delay(500.milliseconds)
    return Random.nextInt(0, 10)
}


suspend fun computeSum(): Int {
    log("computing a sum...")
    val sum = coroutineScope {
        val a = async { generateValue() }
        val b = async { generateValue() }
        a.await() + b.await()
    }

    log("sum is $sum")
    return sum
}

fun main()  {
    val result = runBlocking {
        computeSum()
    }

    print(result)
}