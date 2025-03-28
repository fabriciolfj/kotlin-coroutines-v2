package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WithContextExample {
}

fun main() = runBlocking {
    val result = testRetorno()
    println(result)
}

suspend fun testRetorno(): String {
    return withContext(Dispatchers.Default) {
        "ok"
    }
}