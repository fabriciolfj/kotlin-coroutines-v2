package com.github.fabriciolfj.coroutines.compartilhandorecursos

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

class Example {
}

suspend fun massiveRun(action: suspend () -> Unit) {
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

//val counterContext = newSingleThreadContext("CounterContext")
var counter = AtomicInteger()

fun main() = runBlocking {
    withContext(Dispatchers.Default) { //muda de thread
        massiveRun {
           // withContext(counterContext) {
            //    counter++
            //}
            counter.incrementAndGet()
        }
    }

    println("Counter= $counter")
}