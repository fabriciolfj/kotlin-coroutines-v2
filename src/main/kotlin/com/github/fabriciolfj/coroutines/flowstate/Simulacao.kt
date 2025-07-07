package com.github.fabriciolfj.coroutines.flowstate

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*

class ViewCounter {
    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun increment() {
        _counter.update { it + 1 }
    }
}

fun main() {
    val vc = ViewCounter()
    runBlocking(Dispatchers.Default) {
        repeat(10_000) {
            launch { vc.increment() }
        }
    }

    println(vc.counter.value)
    // 4103
}
