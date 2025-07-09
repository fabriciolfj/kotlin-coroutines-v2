package com.github.fabriciolfj.coroutines.exceptions.example2

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException


fun main() {
    runBlocking {
        val deferred = async {
            delay(100)
            throw RuntimeException("erro no async")
        }

        try {
            val result = deferred.await() //exception lanca aqui
        } catch (e: Exception ) {
            println("capturada ${e.message}" )
        }
    }
}