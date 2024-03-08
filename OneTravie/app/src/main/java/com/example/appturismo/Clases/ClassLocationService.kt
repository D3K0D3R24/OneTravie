package com.example.appturismo.Clases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
class ClassLocationService {
    suspend fun getLocation(context: Context): Location?
    {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!isGPSEnable)
        {
            return null
        }
        return suspendCancellableCoroutine {con ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@suspendCancellableCoroutine
            }
            fusedLocationProviderClient.lastLocation.apply {
                if(isComplete)
                {
                    if(isSuccessful)
                    {
                        con.resume(result){}
                    }
                    else
                    {
                        con.resume(null){}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { con.resume(it){} }
                addOnFailureListener { con.resume(null){} }
                addOnCanceledListener { con.resume(null){} }
            }
        }
    }
}