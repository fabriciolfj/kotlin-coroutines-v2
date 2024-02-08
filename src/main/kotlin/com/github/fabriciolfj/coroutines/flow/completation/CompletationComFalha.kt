package com.github.fabriciolfj.coroutines.flow.completation

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class CompletationComFalha {
}

fun simpleV4() = flow {
    for (i in 1 ..3) {
        emit(i)
        throw RuntimeException()
    }
}

fun main() = runBlocking {
    simpleV4().onCompletion { println("fluxo finalizado, com erro $it") }
        .catch { println("error $it") }
        .collect{ println(it) }
}