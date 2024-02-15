package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

class ConcorrenteSimultaneo {
}


fun main()  = runBlocking {
    val time = measureTime { //calcula em milisegundos, a execucao desse processo automaticamente
        val one = async { doSomethingUsefulOne() }
        val two = async {doSomethingUsefulTwo() }
        println("result ${one.await() + two.await()}")
    }

    println("done time $time")
}
