package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScopeCoroutine {
}

/**
 * runblocking e coroutineScope, ambos esperam que seus filhos concluam as operacoes, para evitar vazamentos
 * runblocking bloqueia a thread, ja o coroutineScope a suspende
 */

fun main() = runBlocking {
    doWorldV2()
}

suspend fun doWorldV2() = coroutineScope {
    launch {
        delay(1000L)
        println("mundo")
    }

    println("olá")
}