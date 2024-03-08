package com.example.appturismo.ui.HomeScreen.RestaurantesScreen

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.Clases.AdapterLugares
import com.example.appturismo.Clases.ClassLocationService
import com.example.appturismo.Clases.DataClassLugares
import com.example.appturismo.R
import com.example.appturismo.data.PlacesApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsFragment : Fragment() {

    private lateinit var context: Context
    private val baseUrl = "https://maps.googleapis.com/maps/api/"
    private lateinit var imgUrl: String
    private lateinit var imageViewFotoLugar: ImageView
    private var myLocation: Location? = null
    private lateinit var coordenadas: String
    private lateinit var adapterLugares: AdapterLugares
    private lateinit var recyclerViewLugares: RecyclerView
    private lateinit var loadingRelativeLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        try {
            return inflater.inflate(R.layout.fragment_restaurants, container, false)
        } catch (excepcion: Exception) {
            // Manejo de la excepción
            Log.e("Error", "Se ha producido una excepción en onCreateView: ${excepcion.message}")
        }
        return null

    }

    override fun onStart() {
        super.onStart()

        val trackerManager = ClassLocationService()
        lifecycleScope.launch {
            myLocation = trackerManager.getLocation(requireContext())
            if(myLocation!=null)
            {
                coordenadas = "${myLocation?.latitude.toString()}, ${myLocation?.longitude.toString()}"
                buscar(coordenadas,10000)
            }
        }
        recyclerViewLugares = requireView().findViewById(R.id.recyclerViewLugares)
        loadingRelativeLayout = requireView().findViewById(R.id.loadingRelativeLayout)
    }

    private fun getRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buscar(location:String, radius: Int)
    {
        lifecycleScope.launch {
            val call = getRetrofit().create(PlacesApiService::class.java).getNearbySearch(location, radius, "restaurant", "AIzaSyA2YK1QpwLMCo2Cf02BBmVuIcRAgXuYSpE")
            val respuesta = call.body()
            requireActivity().runOnUiThread {
                if(call.isSuccessful)
                {
                    val datos:List<DataClassLugares> = respuesta!!.results.map{
                        val imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photo_reference=${it.photos?.get(0)?.photo_reference}&key=AIzaSyA2YK1QpwLMCo2Cf02BBmVuIcRAgXuYSpE"

                        if(it.photos?.get(0)?.photo_reference == null )
                        {
                            DataClassLugares(it.name, it.vicinity, "Abierto", "null", it.rating, myLocation, it.geometry.location, it.place_id)
                        }
                        else
                        {
                            DataClassLugares(it.name, it.vicinity, "Cerrado", imgUrl, it.rating, myLocation, it.geometry.location, it.place_id)
                        }
                    }
                    val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerViewLugares)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,false)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = AdapterLugares(datos)

                    Handler(Looper.getMainLooper()).postDelayed({
                        loadingRelativeLayout.isVisible= false
                        recyclerViewLugares.isVisible = true

                        adapterLugares = AdapterLugares(datos)
                    },1000)
                }
                else
                {
                    //Ha fallado
                }
            }
        }
    }
}