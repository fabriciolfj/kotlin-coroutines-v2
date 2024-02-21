package com.github.fabriciolfj.coroutines.extensionfunctions

import org.apache.coyote.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.List


fun Long.toFormattedDateString() : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(this))
}


fun <T> List<T>.customFilter(predicate: (T) -> Boolean) : List<T> {
    return this.filter(predicate)
}


fun List<Int>.average() : Double {
    return if (isNotEmpty()) {
        sum().toDouble() / size
    } else {
        0.0
    }
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return matches(emailRegex.toRegex())
}

fun File.readText(): String {
    return readText(Charsets.UTF_8)
}

/*
fun Response<*>.isSuccessful(): Boolean {
    return isSuccessful
}*/
