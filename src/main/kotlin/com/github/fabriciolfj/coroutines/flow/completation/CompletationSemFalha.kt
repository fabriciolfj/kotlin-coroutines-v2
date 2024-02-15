package com.github.fabriciolfj.coroutines.flow.completation

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

class CompletationSemFalha {
}

val simple2 = (1 ..3).asFlow()

fun main() = runBlocking {
    simple2.onCompletion { println("fluxo finalizado ") }
        .collect{ println(it) }
}