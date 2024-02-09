package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class `Fan-out` {
}

fun main() = runBlocking {
    var values = producerTime()
    repeat(10) {
        launchProcessor(it, values)
    }

    delay(950)
    values.cancel()
}


fun CoroutineScope.launchProcessor(id: Int, receiveChannel: ReceiveChannel<Int>) = launch {
    for (r in receiveChannel) {
        println("$id processor - value $r")
    }
}

fun CoroutineScope.producerTime() : ReceiveChannel<Int> = produce {
    var i = 0
    while(true) {
        send(i++)
        delay(100)
    }
}