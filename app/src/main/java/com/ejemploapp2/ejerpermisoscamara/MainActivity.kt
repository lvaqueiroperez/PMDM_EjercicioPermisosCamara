package com.ejemploapp2.ejerpermisoscamara

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.jar.Manifest

//CÓDIGO DE SOLICITUD
const val MY_PERMISSIONS_REQUEST_CAMERA = 1;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toast("Se inicia la app")

        //CHECKEO DE PERMISOS Y SOLICITUDES  EN UN BOTÓN
        btnCheck.setOnClickListener { checkPermisoCamara() }


        //SOLICITUD DE PERMISOS


    }

    //UNA VEZ LE DAMOS EL PERMISO, SE MANTIENE PARA SIEMPRE ?
    fun checkPermisoCamara() {

        //HACE FALTA PONER "android" !!
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            toast("HACE FALTA DAR PERMISO")
            //SI NO EXISTEN PERMISOS:


            //SALTAMOS LA EXPLICACIÓN Y SOLICITAMOS EL PERMISO

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA
            )


            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {

            //LOS PERMISOS YA ESTÁN ACTIVOS
            toast("PERMISO ACTIVO")

        }


    }
}
