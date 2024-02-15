package com.github.fabriciolfj.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class FlowCombine {
}

fun main() = runBlocking {
    val numbers = (1 .. 3).asFlow().onEach { delay(300) }
    val chars = flowOf("a", "b", "c").onEach { delay(300) }

    val startTime = System.currentTimeMillis()

    numbers.combine(chars) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}