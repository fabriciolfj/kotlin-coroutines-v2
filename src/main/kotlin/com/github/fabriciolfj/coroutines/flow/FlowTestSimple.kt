package com.github.fabriciolfj.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlowTestSimple {
}


fun simpleV2() = flow {
    for(k in 1..3) {
        delay(100)
        emit(k)
    }
}


fun main() = runBlocking {
    launch {
        for (k in 1 .. 3) {
            println("nao estou bloqueado $k")
            delay(100)
        }
    }
    //println("ok")
    simpleV2().collect { println(it) }

}