package com.github.fabriciolfj.coroutines.exceptions.exemplo4

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val supervisor = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + supervisor)

        scope.launch {
            throw RuntimeException("Falha aqui")
        }

        scope.launch {
            delay(100)
            println("Esta continua executando")
        }

        delay(200)
        supervisor.complete()
    }
}