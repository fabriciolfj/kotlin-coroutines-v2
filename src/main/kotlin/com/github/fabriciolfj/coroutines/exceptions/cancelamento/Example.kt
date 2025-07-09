package com.github.fabriciolfj.coroutines.exceptions.cancelamento

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    delay(100)
                    println("iteracao $i")
                }
            } catch (e: CancellationException) {
                println("coroutine cancelada")
                throw e //importante relancar cancellation exception
            } catch (e: Exception) {
                println("outro erro ${e.message}")
            }
        }

        delay(500)
        job.cancel()
    }
}