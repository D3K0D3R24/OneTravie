package com.example.appturismo.ui.MapScreen

import android.Manifest
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.Clases.AdapterClassHorizontal
import com.example.appturismo.Clases.ClassLocationService
import com.example.appturismo.Clases.ClassPermisos
import com.example.appturismo.Clases.DataClassItemsMaps
import com.example.appturismo.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import kotlinx.coroutines.launch


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var latitud: Double? = null
    var longitud: Double? = null


    private lateinit var vistas: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()

        val checkPermiso = ClassPermisos(requireContext()).checkPermision(
            android.Manifest.permission.ACCESS_FINE_LOCATION,1
        )


        if (checkPermiso == true) {
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.contenedorMap) as SupportMapFragment
            mapFragment.getMapAsync(this@MapFragment)

        } else {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            requireContext().startActivity(intent)
        }

        val botones:List<DataClassItemsMaps> = listOf(
            /*DataClassItemsMaps("Restaurantes",R.drawable.ic_restaurant_24),
            DataClassItemsMaps("Hoteles",R.drawable.ic_hotel_24),
            DataClassItemsMaps("Gasolineras",R.drawable.ic_local_gas_station_24),
            DataClassItemsMaps("Cajeros",R.drawable.ic_atm_24),
            DataClassItemsMaps("Artesanias",R.drawable.ic_table_restaurant_24),*/
        )

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.lista_categorias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        recyclerView.adapter = AdapterClassHorizontal(botones)


        val permiso = ClassPermisos(requireContext())
        permiso.checkPermision(Manifest.permission.ACCESS_FINE_LOCATION,1)

        val mapFragment = childFragmentManager.findFragmentById(R.id.contenedorMap) as SupportMapFragment
        mapFragment.getMapAsync(this@MapFragment)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val trackerManager = ClassLocationService()
        lifecycleScope.launch {
            val result = trackerManager.getLocation(requireContext())
            if(result!=null)
            {
                marker(result)
                //CurrentPlaces(result)
            }
        }
    }


    fun marker(result: Location)
    {
        val lugar = LatLng(result.latitude,result.longitude)
        mMap.addMarker(
            MarkerOptions().position(lugar).title("Mi ubicación")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lugar,14.5f),2000,null)
    }

    /*fun CurrentPlaces (result_Actual: Location){
        Places.initialize(requireContext(), getString(R.string.google_places_key))

        val placesClient = Places.createClient(requireContext())

        val listaComercios = Locales_Location().comercios()
        val information_Location = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES)

        var amigosDBHelper = miSQLiteHelper(requireContext())



        for (lugares in listaComercios)
        {
            //Log.i("Lugar "+listaComercios.listIterator(), lugares.toString())
            val placeid = lugares
            val request = FetchPlaceRequest.newInstance(placeid,information_Location)
            placesClient.fetchPlace(request).addOnSuccessListener {respuesta ->
                val info = respuesta.place

                val lugar = LatLng(info.latLng.latitude,info.latLng.longitude)

                mMap.addMarker(
                    MarkerOptions().position(lugar).title(info.name.toString())
                )
            }
        }

        val ltng = LatLng(result_Actual.latitude,result_Actual.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ltng,18f),2000,null)
    }*/
}