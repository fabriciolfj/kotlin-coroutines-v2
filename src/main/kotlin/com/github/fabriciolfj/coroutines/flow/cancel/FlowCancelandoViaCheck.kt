package com.github.fabriciolfj.coroutines.flow.cancel

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class FlowCancelandoViaCheck {
}

fun simpleNew() = flow {
    for (i in 1 ..3) {
        emit(i)
    }
}

fun main() = runBlocking {
    simpleNew()
        .collect {
            if (it == 2) cancel()
            println(it)
        }
}