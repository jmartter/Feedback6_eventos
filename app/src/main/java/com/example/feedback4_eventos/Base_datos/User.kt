// User.kt
package com.example.feedback4_eventos.Base_datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String = "",
    val password: String = "",
    val novelas: MutableList<Novela> = mutableListOf()
) {
    constructor() : this("", "", mutableListOf())
}