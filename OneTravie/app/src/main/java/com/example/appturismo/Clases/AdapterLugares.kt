package com.example.appturismo.Clases

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.R
import com.example.appturismo.ui.LugarDetailScreen.LugaresDetallesActivity
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterLugares(val list : List<DataClassLugares>):RecyclerView.Adapter<AdapterLugares.viewHolderClass>() {

    var lista = list
    class viewHolderClass(itemView: View, lista: List<DataClassLugares>): RecyclerView.ViewHolder(itemView) {
        lateinit var cardView: CardView
        lateinit var text_view: TextView
        lateinit var text_view_Calle: TextView
        lateinit var text_view_status: TextView
        lateinit var text_view_rating: TextView
        lateinit var img_view: ImageView
        lateinit var imageDefault: ImageView

        init {
            text_view = itemView.findViewById(R.id.txtNombreLugar)
            text_view_Calle = itemView.findViewById(R.id.txtDireccion)
            text_view_status = itemView.findViewById(R.id.txtEstatus)
            text_view_rating = itemView.findViewById(R.id.txtRating)
            img_view = itemView.findViewById(R.id.imgViewLugar)
            cardView = itemView.findViewById(R.id.cardviewContenedor)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderClass {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_lugares_cercanos,parent,false)
        return AdapterLugares.viewHolderClass(vista, list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolderClass, position: Int) {

        val myLocation: MutableList<String>

        holder.text_view.text = list[position].texto
        holder.text_view_Calle.text = list[position].vecyniti
        holder.text_view_status.text = list[position].openingHours
        holder.text_view_rating.text = list[position].rating.toString()
        if(list[position].imgUrl == "null")
        {
            holder.img_view.setImageResource(R.drawable.default_foto)
        }
        else
        {
            Picasso.get().load(list[position].imgUrl).into(holder.img_view)
        }
        holder.cardView.setOnClickListener {
            it.tag = list[position].texto

            var myLocation = doubleArrayOf(
                lista.get(position).myLocation?.latitude ?: 0.0,
                lista.get(position).myLocation?.longitude ?: 0.0
            )

            var placeLocation = doubleArrayOf(
                lista.get(position).placeLocation.lat,
                lista.get(position).placeLocation.lng
            )

            var enviarDatos: Bundle = Bundle().apply {
                putString("imgUrl", lista.get(position).imgUrl)
                putString("texto", lista.get(position).texto)
                putString("vecyniti", lista.get(position).vecyniti)
                putString("openingHours", lista.get(position).openingHours)
                putDouble("rating", lista.get(position).rating)
                putDoubleArray("myLocation",myLocation)
                putDoubleArray("placeLocation", placeLocation)
                putString("placeId", lista.get(position).placeId)
            }

            val intent = Intent(it.context,LugaresDetallesActivity()::class.java)
            intent.putExtras(enviarDatos)
            it.context.startActivity(intent)
        }
    }
}