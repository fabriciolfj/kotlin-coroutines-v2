package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Simultaneo {
}


fun main() = runBlocking{
    doWorldv3()
    println("done")
}

//os launch rodam ao mesmo tempo
suspend fun doWorldv3() = coroutineScope {
    launch {
        delay(2000L)
        println("mundo 2")
    }

    launch {
        delay(1000L)
        println("mundo 1")
    }

    println("olá")
}