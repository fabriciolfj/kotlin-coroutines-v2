package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Example1 {
}

fun main() = runBlocking {
    val channel = Channel<Int>()

    launch {
        for (i in 1..3) {
            channel.send(i);
        }
    }

    repeat(3) { println(channel.receive()) }
    println("done")
}