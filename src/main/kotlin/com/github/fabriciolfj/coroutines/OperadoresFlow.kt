package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OperadoresFlow {
}

suspend fun toMessage(value: Int) : String {
    delay(100)
    return "value transformed $value"
}

fun main() = runBlocking {
    (1 ..3).asFlow().map { toMessage(it) }
            .collect { println(it) }

    println("concluido")

}