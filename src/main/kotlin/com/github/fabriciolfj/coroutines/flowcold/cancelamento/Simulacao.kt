package com.github.fabriciolfj.coroutines.flowcold.cancelamento

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun geValues() =
    flow {
        var i = 0
        while (true) {
            emit(i++)
            delay(100.milliseconds) //ponto de verificacao pra cancelamento
        }
    }


fun main() = runBlocking {
    val collector = launch {
        geValues().collect {
            println(it)
        }
    }
    delay(5.milliseconds)
    collector.cancel()
}