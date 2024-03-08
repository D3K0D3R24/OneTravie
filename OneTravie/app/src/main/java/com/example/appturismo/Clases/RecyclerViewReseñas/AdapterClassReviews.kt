package com.example.appturismo.Clases.RecyclerViewReseñas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.R
import com.squareup.picasso.Picasso

class AdapterClassReviews (val datalist: List<DataClassReviews>, val fragmentManager: FragmentManager): RecyclerView.Adapter<AdapterClassReviews.viewHolder>(){

    val childFragmentManager = fragmentManager

    class viewHolder (itemview: View, childFragmentManager: FragmentManager): RecyclerView.ViewHolder(itemview){
        lateinit var txtNameUser: TextView
        lateinit var txtReview: TextView
        lateinit var fotoPerfil: ImageView
        init {
            txtNameUser = itemview.findViewById(R.id.txtNombreUser)
            txtReview = itemview.findViewById(R.id.txtReseña)
            fotoPerfil = itemview.findViewById(R.id.fototoUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_reviews, parent, false)
        return AdapterClassReviews.viewHolder(vista, fragmentManager)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.txtNameUser.text = datalist[position].nameUser
        holder.txtReview.text = datalist[position].review
        Picasso.get().load(datalist[position].perfilFoto).into(holder.fotoPerfil)
    }
}