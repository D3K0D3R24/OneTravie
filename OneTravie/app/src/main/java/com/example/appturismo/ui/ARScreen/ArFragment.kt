package com.example.appturismo.ui.ARScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.GeolocationPermissions
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.lifecycle.lifecycleScope
import com.example.appturismo.Clases.ClassLocationService
import com.example.appturismo.Clases.ClassPermisos
import com.example.appturismo.R
import com.google.android.gms.location.LocationServices
import com.mapbox.common.location.LocationService
import kotlinx.coroutines.launch

class ArFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ar, container, false)
    }

    override fun onStart() {
        super.onStart()
        val permisoCamera = ClassPermisos(requireContext()).checkPermision(Manifest.permission.CAMERA,2)
        val permisoGPS = ClassPermisos(requireContext()).checkPermision(Manifest.permission.ACCESS_FINE_LOCATION,1)
        val permisoRecordAudio = ClassPermisos(requireContext()).checkPermision(Manifest.permission.RECORD_AUDIO,0)
        val PermisoModiAudio = ClassPermisos(requireContext()).checkPermision(Manifest.permission.MODIFY_AUDIO_SETTINGS,11)

        initializeWebView()
    }

    fun initializeWebView() {
        webView = requireView().findViewById(R.id.webView1)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setGeolocationEnabled(true)
        webSettings.mediaPlaybackRequiresUserGesture = false
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        webSettings.loadsImagesAutomatically = true

        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    webView.loadUrl(url)
                }
                return false
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
                callback?.invoke(origin, true, false)
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)

                Log.e("Bandera", request?.resources?.get(0).toString())
            }

        }
        webView.loadUrl("https://studio.onirix.com/exp/eB0k9L")
    }
    private fun showPermissionDialog(request: PermissionRequest) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Solicitud de permiso")
            .setMessage("La aplicación solicita acceso a la cámara. ¿Desea permitirlo?")
            .setPositiveButton("Sí") { _, _ ->
                // El usuario ha aceptado el permiso
                //request.grant(request.resources)
            }
            .setNegativeButton("No") { _, _ ->
                // El usuario ha denegado el permiso
                request.deny()
            }
            .setCancelable(false) // Evita que el usuario cierre el diálogo sin responder
            .create()

        alertDialog.show()

    }

    private fun permisos() {
        val permisoCamera = ClassPermisos(requireContext()).checkPermision(Manifest.permission.CAMERA,2)
        val permisoGPS = ClassPermisos(requireContext()).checkPermision(Manifest.permission.ACCESS_FINE_LOCATION,1)
        val permisoRecordAudio = ClassPermisos(requireContext()).checkPermision(Manifest.permission.RECORD_AUDIO,0)
        val PermisoModiAudio = ClassPermisos(requireContext()).checkPermision(Manifest.permission.MODIFY_AUDIO_SETTINGS,11)
    }
}
