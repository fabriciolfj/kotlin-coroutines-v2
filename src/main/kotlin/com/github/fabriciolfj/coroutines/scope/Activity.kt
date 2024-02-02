package com.github.fabriciolfj.coroutines.scope

import kotlinx.coroutines.*

class Activity {

    private val scope = MainScope()

    fun destroy() {
        scope.cancel()
    }

    fun doSomething() {
        repeat(10) {
            scope.launch {
                delay((1 + it) * 200L)
                println("coroutine $it is done")
            }
        }
    }
}

fun main() = runBlocking {
    val activity = Activity()
    activity.doSomething()

    println("coroutine lancadas")
    delay(500L)
    println("coroutine destruindo")

    activity.destroy()
    delay(1000)
}