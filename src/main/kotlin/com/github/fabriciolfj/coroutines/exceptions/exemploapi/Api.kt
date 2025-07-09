package com.github.fabriciolfj.coroutines.exceptions.exemploapi

import kotlinx.coroutines.*
import java.io.IOException
import kotlin.random.Random

class ApiService {
    suspend fun buscarDados(): String {
        return try {
            // Simulando uma requisição
            delay(1000)
            if (Random.nextBoolean()) {
                throw IOException("Erro de rede")
            }
            "Dados recebidos"
        } catch (e: IOException) {
            throw ApiException("Falha na requisição: ${e.message}")
        }
    }
}

class ApiException(message: String) : Exception(message)

fun main() {
    val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is ApiException -> println("Erro da API: ${exception.message}")
            else -> println("Erro inesperado: ${exception.message}")
        }
    }

    val scope = CoroutineScope(Dispatchers.Default + handler)

    runBlocking {
        scope.launch {
            val api = ApiService()
            val dados = api.buscarDados()
            println(dados)
        }.join()
    }
}