package com.github.fabriciolfj.coroutines.incluindopontoscancelaveis

import com.github.fabriciolfj.coroutines.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.time.Duration.Companion.milliseconds

suspend fun doCpuHeavyWork(): Int {
    log("I'm doing work!")
    var counter = 0
    val startTime = System.currentTimeMillis()
    while (System.currentTimeMillis() < startTime + 500) {
        counter++
        yield() //ponto de verificacao e permite que o despachante trabalhe em uma corountine diferente se estiver uma aguardando

    }
    return counter
}

fun main() {
    runBlocking {
        val myJob = launch {
            repeat(5) {
               // ensureActive()
                doCpuHeavyWork()
                //if(!isActive) return@launch
                //ensureActive()
            }
        }
        launch {
            repeat(3) {
                doCpuHeavyWork()
            }
        }
        delay(10.milliseconds)
        myJob.cancel()
    }
}
