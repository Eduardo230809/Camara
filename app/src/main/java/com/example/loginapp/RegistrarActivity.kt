package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.PhantomReference

class RegistrarActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        txtName=findViewById(R.id.txtName)
        txtLastName=findViewById(R.id.txtLastname)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        val button=findViewById<Button>(R.id.btnIngresar)
        progressBar=findViewById(R.id.progressBar)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

button.setOnClickListener{
    action()
}

    }

    fun register (view:View) {
        createNewAccount()
    }



    private fun createNewAccount(){
        val Name:String=txtName.text.toString()
        val LastName:String=txtLastName.text.toString()
        val Email:String=txtEmail.text.toString()
        val Password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(LastName) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password) ){

             progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD=dbReference.child(user?.uid!!)

                        userBD.child("Name").setValue(Name)
                        userBD.child("LastName").setValue(LastName)
                        action()
                    }
                }
        }

    }
    fun action () {
        startActivity(Intent(this,LoginActivity2::class.java))
    }


    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task->

                if(task.isComplete){
                    Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(this, "Error al enviar el email", Toast.LENGTH_LONG).show()
                }
            }
    }

}