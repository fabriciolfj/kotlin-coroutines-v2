package com.github.fabriciolfj.coroutines.flow.exceptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class TransaparenciaCatch {
}

fun simple(): Flow<Int> = flow {
    for (i in 1 .. 3) {
        println("emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    simple()
        .catch { e -> println("error $e") } //somente pega as exceptions do upstream
        .collect { value ->
            check(value <= 1) { "valor coletado"} //essa do downstream nao
            println(value)
        }
}