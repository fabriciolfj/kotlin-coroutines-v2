package com.github.fabriciolfj.coroutines


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val channel = Channel<String>()
    launch {
        channel.send("A1")
        channel.send("A2")
        logTest("A done")
    }
    launch {
        channel.send("B1")
        logTest("B done")
    }
    launch {
        repeat(3) {
            val x = channel.receive()
            logTest(x)
        }
    }
}

fun logTest(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}