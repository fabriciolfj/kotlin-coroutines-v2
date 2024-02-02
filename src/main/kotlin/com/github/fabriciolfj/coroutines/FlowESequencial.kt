package com.github.fabriciolfj.coroutines

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    (1 ..5).asFlow()
        .filter {
            println("filtrando $it")
            it % 2 == 0
        }.map {
            println("map $it")
            "string $it"
        }.collect {
            println(it)
        }

}