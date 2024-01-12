package com.example.autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.Registrar
import com.example.autentication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.botonSesion.setOnClickListener{
            login()
        }

        binding.botonRegistrar.setOnClickListener {
            registro()
        }
    }

    private fun registro() {
        startActivity(Intent(this, Registrar::class.java))
    }

    private fun login(){
        //Comprobamos que los campos de correo y campos no esten vacios
        if(binding.correo.text.isNotEmpty() && binding.contraseA.text.isNotEmpty()){
            // Iniciamos sesion con el metodo signIn y enviamos a Firebase el correo y la contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.correo.text.toString(),
                binding.contraseA.text.toString()
            )

                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this,bienvenida::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Correo o password incorrecto", Toast.LENGTH_LONG).show()
                    }
                }
        }
        else{
            Toast.makeText(this,"Algun campo esta vacio", Toast.LENGTH_LONG).show()
        }
    }
}