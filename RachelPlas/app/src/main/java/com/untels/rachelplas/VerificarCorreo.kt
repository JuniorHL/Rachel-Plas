package com.untels.rachelplas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.untels.rachelplas.databinding.ActivityRegisterBinding
import com.untels.rachelplas.databinding.ActivityVerificarCorreoBinding

class VerificarCorreo : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityVerificarCorreoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_verificar_correo)

        binding = ActivityVerificarCorreoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val user = auth.currentUser

        binding.veficateEmailAppCompatButton.setOnClickListener {
            val profileUpdates = userProfileChangeRequest {
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (user.isEmailVerified) {
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                        } else {
                            Toast.makeText(this, "Por favor verifica tu correo.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.imageView.setOnClickListener {
            signOut()
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if (currentUser.isEmailVerified){
                reload();
            }else{
                VerificarCorreo()
            }
        }
    }

    private fun VerificarCorreo() {
        val user = auth.currentUser
        user!!.sendEmailVerification().addOnCompleteListener(this) { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Se envio un correo de verificacion",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun reload(){
        //nos lleva a la pantalla productos
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private  fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, Login::class.java)
        this.startActivity(intent)
    }

}