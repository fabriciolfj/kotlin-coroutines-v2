package com.github.fabriciolfj.coroutines.canais

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

class NumerosPrimos {
}

fun CoroutineScope.numbersFrom(start: Int) : ReceiveChannel<Int> = produce {
    var i = start
    while(true) send(i++)
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, primo: Int) = produce {
    for (n in numbers) {
        if (n % primo != 0) {
            send(n)
        }
    }
}


fun main() = runBlocking {
    var numbers = numbersFrom(2)
    repeat(10) {
        val primo = numbers.receive()
        println(primo)

        numbers = filter(numbers, primo)
    }

    coroutineContext.cancelChildren()
}