package com.example.loginapp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.loginapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val REQUERIMIENTO_GALERIA = 2
    private val REQUERIMIENTO_CAMARA = 3


    var foto: Uri? = null

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        abrirCamaraC()
        abrirGaleria()
    }

    fun abrirCamaraC(){
        binding.btnCamara.setOnClickListener() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    val permisoc = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permisoc, REQUERIMIENTO_CAMARA)
                } else {
                    abrirCamara()
                    mostrarComentario()
                }
            } else {
                abrirCamara()
                mostrarComentario()
            }


        }


    }
    private fun abrirGaleria(){
        binding.btnGaleria.setOnClickListener(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permisog = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permisog,REQUERIMIENTO_GALERIA)
                } else{
                    mostrarGaleria()
                }
            } else{
                mostrarGaleria()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUERIMIENTO_GALERIA ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mostrarGaleria()
                else
                    Toast.makeText(applicationContext,"No tienes acceso", Toast.LENGTH_SHORT).show()
            }
            REQUERIMIENTO_CAMARA ->{
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    abrirCamara()
                else{
                    Toast.makeText(applicationContext,"No tienes acceso", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarGaleria() {
        val mostrar = Intent(Intent.ACTION_PICK)
        mostrar.type = "image/*"
        startActivityForResult(mostrar,REQUERIMIENTO_GALERIA)

    }

    private fun abrirCamara(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE,"Nueva imagen")
        foto= contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val intentarAbrir = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentarAbrir.putExtra(MediaStore.EXTRA_OUTPUT,foto)
        startActivityForResult(intentarAbrir,REQUERIMIENTO_CAMARA)
        Toast.makeText(this, "${binding.etDescripcion.text.toString()}", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUERIMIENTO_GALERIA){
            binding.imageView.setImageURI(data?.data)
        }
        if(resultCode == RESULT_OK && requestCode == REQUERIMIENTO_CAMARA) {
            binding.imageView.setImageURI(foto)
        }
    }

   private fun mostrarComentario(){
       Toast.makeText(this, "${binding.etDescripcion.text.toString()}", Toast.LENGTH_SHORT).show()
    }

}



