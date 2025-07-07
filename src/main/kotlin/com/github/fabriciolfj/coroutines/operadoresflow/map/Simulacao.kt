package com.github.fabriciolfj.coroutines.operadoresflow.map

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking


fun main() {
    val names = flow {
        emit("Jo")
        emit("May")
        emit("Sue")
    }

    val upperAndLowercaseNames = names.transform {
        emit(it.uppercase())
        emit(it.lowercase())
    }

    runBlocking {
        upperAndLowercaseNames.collect { print("$it ")}
    }
}