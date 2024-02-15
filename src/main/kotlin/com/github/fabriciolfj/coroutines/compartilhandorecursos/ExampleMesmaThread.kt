package com.github.fabriciolfj.coroutines.compartilhandorecursos

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class ExampleMesmaThread {
}

suspend fun massiveRunV2(action: suspend () -> Unit) {
    val k = 10000
    val n = 100

    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) {
                        launch {
                            action()
                            println(Thread.currentThread().name)
                        }
                    }
                }
            }
        }
    }
    println("completed ${n * k} actions in $time ms")
}

var counter2 = 0

fun main() = runBlocking {
    val job = launch {
        massiveRunV2 {
            counter2++
        }
    }

    job.join()
    println("Counter= $counter2")
}

suspend fun test() : Unit {
    counter2++
}