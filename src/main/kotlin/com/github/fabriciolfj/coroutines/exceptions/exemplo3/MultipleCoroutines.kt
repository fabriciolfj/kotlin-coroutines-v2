package com.github.fabriciolfj.coroutines.exceptions.exemplo3

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Handler: ${exception.message}")
        }

        val job = launch(handler) {
            // Primeira coroutine
            launch {
                delay(100)
                throw RuntimeException("Erro 1")
            }

            // Segunda coroutine
            launch {
                delay(300)
                println("Esta nao executa")
            }
        }

        job.join()
    }
}