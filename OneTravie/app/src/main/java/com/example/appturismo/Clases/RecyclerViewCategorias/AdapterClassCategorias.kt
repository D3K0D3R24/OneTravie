package com.example.appturismo.Clases.RecyclerViewCategorias

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.Clases.AdapterClassHorizontal
import com.example.appturismo.Clases.DataClassItemsMaps
import com.example.appturismo.R
import com.example.appturismo.ui.HomeScreen.HomeFragment
import com.example.appturismo.ui.HomeScreen.PueblosMagicosScreen.PueblosMagicosFragment
import com.example.appturismo.ui.HomeScreen.RestaurantesScreen.RestaurantsFragment

class AdapterClassCategorias(val datalist: List<DataClassCategorias>, val fragmentManager: FragmentManager): RecyclerView.Adapter<AdapterClassCategorias.viewHolderClass>() {

    var selectedPosition = RecyclerView.NO_POSITION
    val childFragmentManager = fragmentManager

    class viewHolderClass(itemView: View, childFragmentManager: FragmentManager): RecyclerView.ViewHolder(itemView) {
        lateinit var icon: ImageView
        lateinit var texto: TextView
        lateinit var cardview: CardView

        init {
            icon = itemView.findViewById(R.id.iconCategoria)
            texto = itemView.findViewById(R.id.txtNombreCategoria)
            cardview = itemView.findViewById(R.id.cardViewCategorias)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderClass {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_categoria_lugares, parent, false)
        return AdapterClassCategorias.viewHolderClass(vista, fragmentManager)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    var row_index = -1;
    val cornerRadius = 10f
    override fun onBindViewHolder(holder: viewHolderClass, position: Int) {

        holder.icon.setImageResource(datalist[position].idIcon)
        holder.texto.text = datalist[position].texto
        holder.cardview.setOnClickListener{
            holder.cardview.radius = cornerRadius
            row_index = position
            notifyDataSetChanged()
            when(position)
            {
                0 -> return@setOnClickListener
                1 -> openFragments(PueblosMagicosFragment())
                2 -> openFragments(RestaurantsFragment())
            }
        }
        if (row_index==position)
        {
            holder.cardview.setBackgroundColor(Color.parseColor("#807CFF"));
            holder.texto.setTextColor(Color.WHITE)
            holder.texto.setTypeface(null, Typeface.BOLD)
        }
        else
        {
            holder.cardview.setBackgroundColor(Color.WHITE)
            holder.texto.setTextColor(Color.BLACK)
            holder.texto.setTypeface(null, Typeface.NORMAL)
        }
    }
    fun openFragments(fragment: Fragment)
    {
        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container_principal)
        if(!fragment::class.java.isInstance(currentFragment))
        {
            val fragmentTransaction = childFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container_principal, fragment)
            fragmentTransaction?.commit()
        }
    }
}