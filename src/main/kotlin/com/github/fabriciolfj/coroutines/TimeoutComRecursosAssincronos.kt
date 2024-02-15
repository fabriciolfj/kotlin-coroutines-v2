package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class TimeoutComRecursosAssincronos {
}

var acquired = 0
class Resource {

    init {
        acquired++
    }

    fun close() {
        acquired--
    }
}

fun main()  = runBlocking {
    repeat(10_000) {
        launch {
            var resource: Resource ? = null
            try  {
                withTimeout(60) {
                    delay(30)
                    resource = Resource()
                }
            } finally {
                resource?.close()
            }
        }
    }

    println(acquired)
}

