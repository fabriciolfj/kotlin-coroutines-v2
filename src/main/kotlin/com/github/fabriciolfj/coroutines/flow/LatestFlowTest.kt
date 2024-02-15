package com.github.fabriciolfj.coroutines.flow

import com.github.fabriciolfj.coroutines.simpleV7
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*


fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        simpleV7()
            .collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(300) // pretend we are processing it for 300 ms
                println("Done $value")
            }
    }
    println("Collected in $time ms")
}