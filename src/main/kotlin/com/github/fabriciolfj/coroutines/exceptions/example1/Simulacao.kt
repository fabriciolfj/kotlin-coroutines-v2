package com.github.fabriciolfj.coroutines.exceptions.example1

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException




fun main() {
    runBlocking {
        val handler = CoroutineExceptionHandler{_, ex ->
            println("capture exception: ${ex.message}")
        }

        val job = launch(handler) {
            throw RuntimeException("algo deu errado")
        }

        job.join()
    }
}