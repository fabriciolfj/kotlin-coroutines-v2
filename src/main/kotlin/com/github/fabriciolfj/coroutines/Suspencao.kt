package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Suspencao {
}


fun main() = runBlocking {
    doWorld()
    println("ola")

}


suspend fun doWorld() {
    delay(1000L)
    println("mundo")
}