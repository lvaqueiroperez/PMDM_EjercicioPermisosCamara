package com.ejemploapp2.ejerpermisoscamara

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.jar.Manifest

//CÓDIGOS DE SOLICITUD (TIENEN QUE TENER VALORES DISTINTOS !!!)
const val MY_PERMISSIONS_REQUEST_CAMERA = 1;
const val REQUEST_IMAGE_CAPTURE = 2;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toast("Se inicia la app")

        //CHECKEO DE PERMISOS Y SOLICITUDES  EN UN BOTÓN
        btnCheck.setOnClickListener { checkPermisoCamara() }

        btnFoto.setOnClickListener{dispatchTakePictureIntent()}


    }

    //SOLICITUD DE PERMISOS
    //UNA VEZ LE DAMOS EL PERMISO, TENEMOS QUE QUITARLO PARA QUE VUELVA A PREGUNTAR POR ÉL
    fun checkPermisoCamara() {

        //HACE FALTA PONER "android" !!
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //SI NO EXISTEN PERMISOS:
            toast("HACE FALTA DAR PERMISO")


            //SALTAMOS LA EXPLICACIÓN DE POR QUÉ LO NECESITAMOS Y SOLICITAMOS EL PERMISO DIRECTAMENTE

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA
            )

            //SI LOS PERMISOS YA ESTÁN ACTIVOS
        } else {


            toast("PERMISO ACTIVO")

        }


    }

    //PARA HACER QUE SAQUE UNA FOTO Y LA MUESTRE POR PANTALLA:
    //LA CÁMARA ES UNA ACTIVITY, POR ESO HACEMOS EL "onActivityResult"

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    //OJO!! EN "onActivityResult" PODEMOS RECOGER VARIOS DATOS DE FUNCIONES DISTINTAS, POR ESO SE UTILIZAN LOS REQUEST CODE
    //CON VALORES DISTINTOS
    //TRAS SACAR LA FOTO, SE LLAMARÁ A ESTA FUNCIÓN PARA OBTENERLA EN LA "IMAGEVIEW"
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //EN KOTLIN, CUANDO MANEJAMOS VARIABLES QUE PUEDEN SER NULL (data en este caso),
            //KOTLIN SIEMPRE NOS OBLIGARÁ A TRATAR ESOS VALORES QUE PUEDEN SER NULL DE ALGUNA MANERA
            //(por ej, con un "if" que controle si la variable es null o no (if...else...))
            //SI DE ALGUNA MANERA SABEMOS QUE ESA VARIABLE NO VA A SER NULL NUNCA, PODEMOS USAR EL OPERADOR "!!"
            //
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            image.setImageBitmap(imageBitmap)
        }
    }

    //EN LA DOCUMENTACIÓN HAY MANERAS PARA GUARDARLA EN LA GALERÍA Y MÁS FUNCIONES...


}
