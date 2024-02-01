package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.*

class TimeoOutCancelamento {
}

fun main() = runBlocking {

    val result =
        //withTimeout(1300L) { //lanca uma exception
        withTimeoutOrNull(1300L) { //retorna null quando estoura o timeout
            repeat(1000) {
                println("i am sleeping $it")
                delay(500L)
            }

            "Done"
        }

    println("is result $result")
}