package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Basico {
}


fun main() = runBlocking{
    launch {
        delay(1000L)
        println("world")
    }

    println("hello")
}