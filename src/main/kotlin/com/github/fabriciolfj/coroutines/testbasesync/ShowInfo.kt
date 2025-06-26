package com.github.fabriciolfj.coroutines.testbasesync

import java.time.Duration


fun login(value: String) : String {
    Thread.sleep(Duration.ofSeconds(3))
    return "login"
}

fun loadUer(value: String) : String {
    Thread.sleep(Duration.ofSeconds(5))
    return "loadUser"
}

class ShowInfo {
}

fun main() {
    val inicio = System.currentTimeMillis()

    val user = login("test")
    val data = loadUer(user)

    val fim = System.currentTimeMillis()

    println("$user - $data")

    println((fim - inicio) / 1000)
}