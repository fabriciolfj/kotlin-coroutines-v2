package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CanalBuffer {
}


fun main() = runBlocking {
    val channel = Channel<Int>(4)

    launch {
        repeat(10) {
            println("sending $it")
            channel.send(it)
        }
    }

    delay(1000)

    channel.cancel()
    println("done")
}