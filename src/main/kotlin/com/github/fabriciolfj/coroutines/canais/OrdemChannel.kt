package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.core.codec.Hints

class OrdemChannel {
}

data class Ball(var hits: Int)

fun main() = runBlocking {
    var channel = Channel<Ball>()
    launch { player(channel, "ping") }
    launch { player(channel, "pong") }

    channel.send(Ball(0))
    delay(1000)
    coroutineContext.cancelChildren()
}


suspend fun player(channel: Channel<Ball>, name: String) {
    for(c in channel) {
        c.hits++
        println("$name $c")
        delay(300)
        channel.send(c)
    }
}