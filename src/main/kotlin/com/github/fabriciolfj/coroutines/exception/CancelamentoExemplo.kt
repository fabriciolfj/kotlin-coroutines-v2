package com.github.fabriciolfj.coroutines.exception

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

class CancelamentoExemplo {
}


fun main() = runBlocking {

    val job = launch {
        val children = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("children cancel")
            }
        }

        yield()
        println("cancelando filho")
        children.cancel()
        children.join()
        yield()
        println("pai nao foi cancelado")
    }

    job.join()

}