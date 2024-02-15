package com.github.fabriciolfj.coroutines.exception

import kotlinx.coroutines.*
import java.io.IOException

class AgregandoExceptionsV2 {
}

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, ex -> println("CoroutineExceptionHandler exception capturada $ex") }

    val job = GlobalScope.launch(handler) {
        val innerJob = launch {
            launch {
                launch {
                    throw IOException()
                }
            }
        }

        try {
            innerJob.join()
        } catch (e: CancellationException) {
            println("lancando a causa raiz")
            throw e
        }
    }

    job.join()

}