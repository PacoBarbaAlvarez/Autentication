package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autentication.bienvenida
import com.example.autentication.databinding.ActivityMainBinding
import com.example.autentication.databinding.ActivityRegistrarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registrar : AppCompatActivity() {

    lateinit var binding: ActivityRegistrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bregistra.setOnClickListener{
            val db= FirebaseFirestore.getInstance()

            // COMPROBAMOS Q NO ESTA VACIO
            if(binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty() && binding.correoE.text.isNotEmpty() && binding.contrasena.text.isNotEmpty())
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.correoE.text.toString(), binding.contrasena.text.toString()
                )
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            db.collection("usuarios").document(binding.correoE.text.toString())
                                .set(mapOf(
                                    "nombre" to binding.nombre.text.toString(),
                                    "apellido" to binding.apellidos.text.toString()
                                ))

                            startActivity(Intent(this, bienvenida::class.java))
                        }
                        else{
                            Toast.makeText(this,"No se ha  podido registrar el usuario", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG).show()
            }
        }

    }
}