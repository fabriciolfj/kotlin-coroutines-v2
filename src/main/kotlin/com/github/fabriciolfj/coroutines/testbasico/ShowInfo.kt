package com.github.fabriciolfj.coroutines.testbasico

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.yaml.snakeyaml.util.Tuple
import kotlin.time.Duration
import kotlin.time.measureTime


suspend fun login(value: String) : String {
    delay(3000)
    return "login"
}

suspend fun loadUer(value: String) : String {
    delay(5000)
    return "loadUser"
}

class ShowInfo {
}

fun main() {
    val inicio = System.currentTimeMillis()

    val (user, data) = runBlocking {
        val user = async {  login("test") }
        val data = async { loadUer("teste") }

        val result = awaitAll(user, data)

        Pair(result[0], result[1])
    }

    val fim = System.currentTimeMillis()

    println("$user - $data")

    println((fim - inicio) / 1000)
}