package com.github.fabriciolfj.coroutines.flowhot.share

import com.github.fabriciolfj.coroutines.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.*
import kotlin.time.Duration.Companion.milliseconds

class RadioStation {
    private val _messageFlow = MutableSharedFlow<Int>(5)
    val messageFlow = _messageFlow.asSharedFlow()

    fun beginBroadcasting(scope: CoroutineScope) {
        scope.launch {
            while(true) {
                delay(500.milliseconds)
                val number = Random.nextInt(0..10)
                log("Emitting $number!")
                _messageFlow.emit(number)
            }
        }
    }
}

fun main(): Unit = runBlocking {
    val radio = RadioStation()
    radio.beginBroadcasting(this)
    delay(5.milliseconds)

    launch {
        radio.messageFlow.collect {
            log("A collection $it!")
        }
    }

    launch {
        radio.messageFlow.collect {
            log("B collection $it!")
        }
    }
}
