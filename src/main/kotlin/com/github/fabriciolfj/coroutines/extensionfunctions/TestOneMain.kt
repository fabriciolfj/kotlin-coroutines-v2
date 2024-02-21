package com.github.fabriciolfj.coroutines.extensionfunctions

class TestOneMain {
}


fun main() {
    val timestamp = System.currentTimeMillis()
    val formattedDate = timestamp.toFormattedDateString()

    //println(formattedDate)


    val dados = arrayListOf(1, 2, 3, 4, 5, 6)
    val news = dados.customFilter { it % 2 == 0 }

    println(news)
}