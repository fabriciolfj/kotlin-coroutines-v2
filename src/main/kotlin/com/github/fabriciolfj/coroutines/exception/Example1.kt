package com.github.fabriciolfj.coroutines.exception

import kotlinx.coroutines.*

class Example1 {
}

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {

    val job = GlobalScope.launch {
        println("lancamento de exception desde o lancamento")
        throw IndexOutOfBoundsException()
    }

    /* nao cai no cath
    try {
        job.join()
    } catch (e: IndexOutOfBoundsException) {
        println("capturado o IndexOutOfBoundsException")
    }*/

    println("job com falha no inicio")
    val adiado = GlobalScope.async {
        println("lancamento excecao no assincrono")
        throw ArithmeticException()
    }

    try {
        adiado.await()
        println("nao lancado")
    } catch (e: ArithmeticException) {
        println("capturada arithmeticaException")
    }

}