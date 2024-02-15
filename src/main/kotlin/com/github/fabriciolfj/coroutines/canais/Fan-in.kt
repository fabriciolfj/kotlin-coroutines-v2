package com.github.fabriciolfj.coroutines.canais

import com.github.fabriciolfj.coroutines.flow.cancel.simpleNew
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class `Fan-in` {
}

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while(true) {
        delay(time)
        channel.send(s)
    }
}


fun main() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "boo", 300L) }
    launch { sendString(channel, "faa", 250L) }

    repeat(10) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()
}

