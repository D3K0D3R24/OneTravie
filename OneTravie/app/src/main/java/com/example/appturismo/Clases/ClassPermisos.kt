package com.example.appturismo.Clases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ClassPermisos(val context: Context) : AppCompatActivity() {

    private var codigoPermiso: Int? = null

    fun checkPermision(permiso: String, cod_permiso: Int): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                permiso
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Toast.makeText(context, "El permiso ha sido otorgado",Toast.LENGTH_LONG).show()
            return true
        } else {
            //EL permiso no ha sido otorgado, solicitar permiso
            requestPermiso(permiso, cod_permiso)
            return false
        }
    }

    public fun requestPermiso(permiso: String, codigo: Int) {
        codigoPermiso = codigo
        ActivityCompat.requestPermissions(context as Activity, arrayOf(permiso), codigo)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == codigoPermiso) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

                /*val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)*/

                requestPermiso(permissions[0], requestCode)

            }
        }
    }
}