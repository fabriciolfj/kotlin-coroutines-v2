package com.github.fabriciolfj.coroutines.flow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

class FluxoZip {
}

fun main() = runBlocking {
    val numbers = (1 .. 3).asFlow()
    val chars = flowOf("a", "b", "c")
    numbers.zip(chars){n, l -> "$n - $l"}
        .collect{ println(it) }

}