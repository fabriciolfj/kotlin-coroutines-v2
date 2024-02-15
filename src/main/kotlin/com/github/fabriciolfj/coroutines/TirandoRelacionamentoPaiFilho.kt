package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TirandoRelacionamentoPaiFilho {
}

fun main() = runBlocking {
    val request = launch {
        launch(Job()) {
            println("job1: inicio")
            delay(1000)
            println("job1: nao paro, pq nao tenho mais vinculo")
        }

        launch {
            println("job2: inicio")
            delay(1000)
            println("job2: paro, pq tenho vinculo")
        }
    }

    delay(500)
    request.cancel()
    println("main: quem sobreviveu")
    delay(1000)
}