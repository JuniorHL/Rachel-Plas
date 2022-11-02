package com.untels.rachelplas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.untels.rachelplas.databinding.ActivityRecuperarCuentaBinding

class RecuperarCuenta : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_recuperar_cuenta)

        binding = ActivityRecuperarCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.senEmailAppCompatButton.setOnClickListener {
            val emailAddress = binding.emailEditText.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, Login::class.java)
                        this.startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Ingrese un email de una cuenta valida.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}