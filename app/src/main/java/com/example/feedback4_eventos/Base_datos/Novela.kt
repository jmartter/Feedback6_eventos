// Novela.kt
package com.example.feedback4_eventos.Base_datos

data class Novela(
    val titulo: String = "",
    val autor: String = "",
    val anoPublicacion: Int = 0,
    val sinopsis: String = "",
    var isFavorite: Boolean = false,
    val reseñas: MutableList<String> = mutableListOf()
)