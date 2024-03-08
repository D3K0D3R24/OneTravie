package com.example.appturismo.ui.LugarDetailScreen

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.Clases.AdapterLugares
import com.example.appturismo.Clases.DataClassLugares
import com.example.appturismo.Clases.RecyclerViewReseñas.AdapterClassReviews
import com.example.appturismo.Clases.RecyclerViewReseñas.DataClassReviews
import com.example.appturismo.R
import com.example.appturismo.data.PlaceDetailProvider.PlaceDetailService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LugaresDetallesActivity : AppCompatActivity() {

    private var namePlace: String? = null
    private var callePlace: String? = null
    private val baseUrl = "https://maps.googleapis.com/maps/api/"
    private lateinit var fotoLugar: ImageView
    private lateinit var txtNombreLugar: TextView
    private lateinit var txtCalle: TextView
    private lateinit var txtHorarios: TextView

    private lateinit var imgUrlList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_detalles)
    }

    override fun onStart() {
        super.onStart()
        var recibirDatos = intent.extras

        //Recuperacion de datos enviados
        var imgUrl = recibirDatos?.getString("imgUrl")
        namePlace = recibirDatos?.getString("texto")
        callePlace = recibirDatos?.getString("vecyniti")
        var status = recibirDatos?.getString("openingHours")
        var rating = recibirDatos?.getDouble("rating")
        var myLocation = Location("myLocation").apply {
            latitude = recibirDatos?.getDoubleArray("myLocation")?.get(0) ?: 0.0
            longitude = recibirDatos?.getDoubleArray("myLocation")?.get(1) ?: 0.0
        }
        var placeLocation = Location("placeLocation").apply {
            latitude = recibirDatos?.getDoubleArray("placeLocation")?.get(0) ?: 0.0
            longitude = recibirDatos?.getDoubleArray("placeLocation")?.get(1) ?: 0.0
        }
        var placeId = recibirDatos?.getString("placeId")?:""

        fotoLugar = findViewById(R.id.imgViewPhotoPlace)
        txtNombreLugar = findViewById(R.id.txtNombreLocal)
        txtCalle = findViewById(R.id.txtCalle)
        txtHorarios = findViewById(R.id.txtHorarios)

        Toast.makeText(this,placeId,Toast.LENGTH_LONG).show()
        getPlaceDetails(placeId)
    }

    private fun getPlaceDetails(placeId: String) {
        imgUrlList = mutableListOf()
        lifecycleScope.launch {
            val call = getRetrofit().create(PlaceDetailService::class.java).getPlaceDetails(placeId,"AIzaSyA2YK1QpwLMCo2Cf02BBmVuIcRAgXuYSpE")
            val respuesta = call.body()
            respuesta?.result?.photos?.forEachIndexed { index, photo ->
                imgUrlList.add(index, photo.photo_reference)
            }
            val datos:List<DataClassReviews> = respuesta!!.result.reviews.map{
                DataClassReviews("D3K0D3R","Esta es la reseña del lugar, por favor asdasdn asdasdasd asdasdasd ","htttpsasdasdasdasd")
            }
            runOnUiThread {
               if(call.isSuccessful)
                {
                    val imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photo_reference=${imgUrlList.get(0)}&key=AIzaSyA2YK1QpwLMCo2Cf02BBmVuIcRAgXuYSpE"
                    Picasso.get().load(imgUrl).into(fotoLugar)
                    txtNombreLugar.text = namePlace
                    txtCalle.text = callePlace
                    txtHorarios.text = respuesta?.result?.current_opening_hours?.weekday_text?.joinToString("\n")




                    val recyclerView = this@LugaresDetallesActivity.findViewById<RecyclerView>(R.id.recyclerViewReseñas)
                    recyclerView.layoutManager = LinearLayoutManager(this@LugaresDetallesActivity,
                        LinearLayoutManager.VERTICAL,false)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = AdapterClassReviews(datos, supportFragmentManager )
                }
                else
                {
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

