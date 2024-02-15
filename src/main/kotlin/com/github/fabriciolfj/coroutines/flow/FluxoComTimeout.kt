package com.github.fabriciolfj.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class FluxoComTimeout {
}

fun simpleV3() = flow {
    for (k in 1 .. 3) {
        delay(100)
        println("emitindo $k")
        emit(k)
    }
}


fun main() = runBlocking {
    withTimeoutOrNull(250) {
        simpleV3().collect{ println(it) }
    }

    println("concluido")
}