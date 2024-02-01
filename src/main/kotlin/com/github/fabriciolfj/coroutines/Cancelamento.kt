package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.*

class Cancelamento {
}


fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job is sleep $i...")
            delay(50L)
        }
    }

    delay(1600L)
    println("principal i am tired waiting")
    job.cancelAndJoin() //aguarda o que esta executando e em seguida cancela
    //job.cancel()
    //job.join()
    println("main exit now")
}