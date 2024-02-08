package com.github.fabriciolfj.coroutines.flow.terminal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class FlowLaunchIn {
}

fun simpleV8() = flow {
    for (i in 1 ..3) {
        emit(i)
    }
}

fun main() = runBlocking {
    simpleV8().onEach { println(it) }
        .launchIn(this)
        //.launchIn(CoroutineScope(Dispatchers.Default))

    println("done")
}