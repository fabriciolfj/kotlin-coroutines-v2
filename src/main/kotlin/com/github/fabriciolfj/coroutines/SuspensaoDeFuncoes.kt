package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SuspensaoDeFuncoes {
}

suspend fun simple() : List<Int> {
    delay(100)
    return listOf(1 , 2, 3)
}

fun main() = runBlocking {
    simple().forEach{ println(it) }

}