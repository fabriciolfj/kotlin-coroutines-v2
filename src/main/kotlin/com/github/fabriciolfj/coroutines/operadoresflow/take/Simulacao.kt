package com.github.fabriciolfj.coroutines.operadoresflow.take

import com.github.fabriciolfj.coroutines.coroutinescomretorno.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun querySensor(): Int = Random.nextInt(-10..30)

fun getTemperatures(): Flow<Int> {
    return flow {
        while(true) {
            emit(querySensor())
            delay(500.milliseconds)
        }
    }
}


fun main() {
    val temps = getTemperatures()
    runBlocking {
        temps.take(20)
            .debounce { 250.milliseconds } // tempo para comecar a processar os eventos, mas eles ja sao emitidos, implicando em perda
            .buffer(3, onBufferOverflow = BufferOverflow.SUSPEND)
            .onEmpty {
                println("Nothing - emitting default value!")
                emit(0)
            }
            .onStart {
                println("Starting!")
            }
            .flowOn(Dispatchers.Default)
            .onEach {
                println("On $it!")
            }
            .flowOn(Dispatchers.IO)
            .onEach {
                println("on 2 $it")
            }
            .conflate() //descarta elementos quando o colletor estiver ocupado
            .onCompletion { cause ->
                if (cause != null) {
                    println("an error occurred! $cause")
                } else {
                    println("completed!")
                }
            }
            .collect {
                log(it)
                delay(1.seconds)
            }
    }
}