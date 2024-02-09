package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FechamentoCanais {
}


fun CoroutineScope.produce() : ReceiveChannel<Int> = produce {
    for (i in 1..5) {
        send(i);
    }
}

fun main() = runBlocking {
    val channel = produce()

   /* launch {
        for (i in 1..5) { //continua processando caso de alguma falha
            channel.send(i);
        }

        channel.close()
    }*/

    /*for (y in channel) {
        println(y)
    }*/


    channel.consumeEach { println(it) } //fecha o canal quando concluir o consumo ou se der alguma falha
    println("done")
}