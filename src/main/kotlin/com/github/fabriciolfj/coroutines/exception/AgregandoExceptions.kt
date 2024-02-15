package com.github.fabriciolfj.coroutines.exception

import kotlinx.coroutines.*
import java.io.IOException

class AgregandoExceptions {
}

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler{ _, ex -> println("corountineExceptionHandler obteve $ex, com suprimido ${ex.suppressed.contentToString()}") }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                throw ArithmeticException()
            }
        }

        launch {
            delay(100)
            throw IOException()
        }

        delay(Long.MAX_VALUE)
    }

    job.join()
}