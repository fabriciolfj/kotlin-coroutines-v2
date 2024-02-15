package com.github.fabriciolfj.coroutines.exception

import kotlinx.coroutines.*

class SupervisorExample {
}

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    //Então é uma forma concisa de alterar temporariamente o contexto de corrotina para adicionar o comportamento customizado de supervisor, sem afetar código fora do with. Uma vez que saímos do with, o contexto global padrão é restaurado.
    with(CoroutineScope(coroutineContext + supervisor)) {
        val primeiroFilho = launch { CoroutineExceptionHandler{ _, _ ->
            println("primeiro filho esta falhando") }
            throw AssertionError("primeiro filho foi cancelado")
        }

        val segundoFilho = launch {
            primeiroFilho.join()
            println("primeiro filho foi cancelado ${primeiroFilho.isCancelled}, mas o segundo ainda esta vivo")

            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("segundo filho foi cancelado porque o supervisor foi cancelado")
            }
        }

        primeiroFilho.join()
        println("cancelando o supervisor")
        supervisor.cancel()
        segundoFilho.join()
    }
}