package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.*

class ThreadLocalTest {
}

var threadLocal = ThreadLocal<String>()
fun main() = runBlocking {
    threadLocal.set("main")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement("other")) {
        println("valor threadLocal ${threadLocal.get()}")
        yield()
        println("after yield threadLocal ${threadLocal.get()}")
    }

    job.join()
    println("after ${threadLocal.get()}")
}