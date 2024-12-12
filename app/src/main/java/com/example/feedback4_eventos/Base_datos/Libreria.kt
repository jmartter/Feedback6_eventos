package com.example.feedback4_eventos.Base_datos

data class Libreria(val nombre: String, val direccion: String)

object LibreriaManager {
    private val librerias = listOf(
        Libreria("La Central", "Calle del Postigo de San Martín, 8, 28013 Madrid"),
        Libreria("Casa del Libro (Gran Vía)", "Gran Vía, 29, 28013 Madrid"),
        Libreria("Tipos Infames", "Calle de San Joaquín, 3, 28004 Madrid"),
        Libreria("Rafael Alberti", "Calle del Tutor, 57, 28008 Madrid"),
        Libreria("Desperate Literature", "Calle del Campomanes, 13, 28013 Madrid"),
        Libreria("Librería Mujeres", "Calle de San Cristóbal, 17, 28012 Madrid"),
        Libreria("Ocho y Medio Libros de Cine", "Calle de Martín de los Heros, 11, 28008 Madrid"),
        Libreria("Traficantes de Sueños", "Calle del Duque de Alba, 13, 28012 Madrid"),
        Libreria("Panta Rhei", "Calle del Hernán Cortés, 7, 28004 Madrid"),
        Libreria("Antonio Machado", "Calle de Fernando VI, 17, 28004 Madrid")
    )

    fun getRandomLibreria(): Libreria {
        return librerias.random()
    }
}