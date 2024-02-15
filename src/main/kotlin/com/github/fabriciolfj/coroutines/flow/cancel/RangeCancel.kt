package com.github.fabriciolfj.coroutines.flow.cancel

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.runBlocking

class RangeNotCancel {
}

/*
fun main() = runBlocking<Unit> {
    (1..5).asFlow().collect { value ->
        if (value == 3) cancel() //nao faz verificacoes
        println(value)
    }
}*/


//corrigindo
fun main() = runBlocking<Unit> {
    (1..5).asFlow().cancellable().collect { value ->
        if (value == 3) cancel()
        println(value)
    }
}