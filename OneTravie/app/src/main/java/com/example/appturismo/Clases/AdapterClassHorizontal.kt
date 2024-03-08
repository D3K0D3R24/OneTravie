package com.example.appturismo.Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.R
import com.google.android.material.tabs.TabLayout


class AdapterClassHorizontal(val datalist: List<DataClassItemsMaps>): RecyclerView.Adapter<AdapterClassHorizontal.viewHolderClass>()
{
    class viewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var  btn: Button

        init {

            btn = itemView.findViewById(R.id.botonCategory)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderClass {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_category_maps,parent,false)
        return viewHolderClass(vista)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: viewHolderClass, position: Int) {
        holder.btn.text = datalist[position].texto
        holder.btn.setCompoundDrawablesWithIntrinsicBounds(datalist[position].idIcon,0,0,0)
    }
}