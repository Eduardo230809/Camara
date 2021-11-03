package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        txtEmail=findViewById(R.id.txtEmail)
        progressBar=findViewById(R.id.progressBar)
        auth= FirebaseAuth.getInstance()
    }

    fun send(view:View){
        val Email=txtEmail.text.toString()
        if (!TextUtils.isEmpty(Email)){
            auth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(this){
                        task->
                    if(task.isSuccessful){
                        progressBar.visibility=View.VISIBLE
                        startActivity(Intent(this,LoginActivity2::class.java))
                    }else{
                        Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show()
                    }

                }
        }
    }


        }