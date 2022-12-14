package com.untels.rachelplas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.untels.rachelplas.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.Registrarse.setOnClickListener {

            val mEmail = binding.Email.text.toString()
            val mPassword = binding.ContraseA.text.toString()
            val mRepeatPassword = binding.RepetirContra.text.toString()

            val passwordRegex = Pattern.compile("^" +
                    "(?=.*[‐@#$%^&+=])" +     // Al menos 1 carácter especial
                    ".{6,}" +                // Al menos 4 caracteres
                    "$")

            if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                Toast.makeText(baseContext, "Ingrese un email valido", Toast.LENGTH_SHORT).show()
            }else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
                Toast.makeText(this, "La contraseña es debil.",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword != mRepeatPassword){
                Toast.makeText(this, "Confirma la contraseña.",
                    Toast.LENGTH_SHORT).show()
            } else {
                CuentaNueva(mEmail, mPassword)
            }
        }

        binding.volver.setOnClickListener{
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            } else {
                val intent = Intent(this, VerificarCorreo::class.java)
                this.startActivity(intent)
            }
        }
    }

    //funcion crear cuenta nueva
    private fun CuentaNueva(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, VerificarCorreo::class.java)
                    this.startActivity(intent)
                } else {
                    Toast.makeText(this, "No se pudo crear la cuenta. Vuelva a intertarlo",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        //nos lleva a la pantalla productos
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}