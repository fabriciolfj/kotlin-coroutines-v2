package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UsandoJoin {
}

/**
 * espra o lanch concluir
 */

fun main()  = runBlocking{
    val job = launch {
        delay(100L)
        println("mundo")
    }

    print("olá")
    job.join()
    println("done")
}