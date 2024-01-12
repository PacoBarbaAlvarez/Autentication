package com.example.autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autentication.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Bienvenida"
        val extras = intent.extras
        val nombre = extras?.getString("NombreUsuario")
        binding.bienvenida.text = "Bienvenido, $nombre"

        val db= FirebaseFirestore.getInstance()


        /*
        val db = FirebaseFirestore.getInstance()

        binding.botonGuardar.setOnClickListener {
            if (binding.matricula.text.isNotEmpty() &&
                binding.marca.text.isNotEmpty() &&
                binding.modelo.text.isNotEmpty() &&
                binding.color.text.isNotEmpty()) {
                db.collection("Coches").add(mapOf(
                "color" to binding.color.text.toString(),
                "marca" to binding.marca.text.toString(),
                "matricula" to binding.matricula.text.toString(),
                "modelo" to binding.modelo.text.toString()
                ))

                    .addOnSuccessListener {documento->
                        Toast.makeText(this, "Nuevo coche añadido con el id: ${documento.id}",Toast.LENGTH_LONG).show()
                    }

                    .addOnFailureListener{
                        Toast.makeText(this, "Error en la inserccion del nuevo registro",Toast.LENGTH_LONG).show()
                    }

            } else {
                Toast.makeText(this, "Algun camo esta vacio", Toast.LENGTH_LONG).show()
            }
            */

        binding.botonGuardar.setOnClickListener {
            if(binding.matricula.text.isNotEmpty() &&
                binding.marca.text.isNotEmpty() &&
                binding.modelo.text.isNotEmpty() &&
                binding.color.text.isNotEmpty()){
                db.collection("coches").add(mapOf(
                    "color" to binding.color.text.toString(),
                    "marca" to binding.marca.text.toString(),
                    "matricula" to binding.matricula.text.toString(),
                    "modelo" to binding.modelo.text.toString()
                )
                )
                db.collection("coches").document(binding.matricula.text.toString())
                    .set(mapOf(
                        "color" to binding.color.text.toString(),
                        "marca" to binding.marca.text.toString(),
                        "modelo" to binding.modelo.text.toString()
                    )
                    )
            }
            else{
                Toast.makeText(this, "Algún campo esta vacío", Toast.LENGTH_LONG).show()
            }
        }

            binding.botonCerrar.setOnClickListener {
                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this, MainActivity::class.java))
            }

            binding.botonEditar.setOnClickListener {
                db.collection("coches")
                    .whereEqualTo("matricula", binding.matricula.text.toString())
                    .get().addOnSuccessListener {
                        it.forEach{
                            binding.marca.setText(it.get("marca")as String?)
                            binding.modelo.setText(it.get("modelo")as String?)
                            binding.color.setText(it.get("color")as String?)
                        }
                    }
            }
/*
            binding.botonEliminar.setOnClickListener {
               db.collection("coches")
                   .get()
                   .addOnSuccessListener {
                       it.forEach{
                           it.reference.delete()
                       }
                   }
            }
        */

        binding.botonEliminar.setOnClickListener {
            db.collection("coches")
                .document(binding.matricula.text.toString())
                .delete()
        }
    }
}