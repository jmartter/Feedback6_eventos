package com.example.feedback4_eventos
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.feedback4_eventos.Inicio.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Firestore
        val db = FirebaseFirestore.getInstance()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
// inicio