package com.example.appturismo.ui.HomeScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismo.Clases.AdapterLugares
import com.example.appturismo.Clases.ClassPermisos
import com.example.appturismo.Clases.RecyclerViewCategorias.AdapterClassCategorias
import com.example.appturismo.Clases.RecyclerViewCategorias.DataClassCategorias
import com.example.appturismo.R
import com.example.appturismo.ui.HomeScreen.HotelesScreen.HotelesFragment
import com.example.appturismo.ui.HomeScreen.PueblosMagicosScreen.PueblosMagicosFragment
import com.example.appturismo.ui.HomeScreen.RestaurantesScreen.RestaurantsFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.tabs.TabLayout
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeFragment : Fragment() {

    private lateinit var context: Context
    private var selectedTabIndex = -1
    private var indicador: Boolean = false
    private lateinit var adapterClassCategorias: AdapterClassCategorias
    private lateinit var bar_navigation_category: AnimatedBottomBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext()
    }

    override fun onStart() {
        super.onStart()
        permisos()


        val datos:List<DataClassCategorias> = listOf(
            DataClassCategorias("Top",R.drawable.outline_star_border_24),
            DataClassCategorias("P.Magicos", R.drawable.rehilete),
            DataClassCategorias("Restaurantes", R.drawable.outline_restaurant_24),
            DataClassCategorias("Hoteles", R.drawable.outline_star_border_24)
            //DataClassCategorias("Hoteles", R.drawable.hotel64)
        )
        bar_navigation_category = requireView().findViewById<AnimatedBottomBar>(R.id.bar_navigation_category)
        bar_navigation_category.onTabSelected = {
            when(it.title)
            {
                "Restaurantes" -> openFragments(RestaurantsFragment())
                "Hoteles" -> openFragments(HotelesFragment())
            }
        }
        markOption()

        /*val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)

        recyclerView.adapter = AdapterClassCategorias(datos, childFragmentManager)
        adapterClassCategorias = AdapterClassCategorias(datos, childFragmentManager)

        val tabLayout = requireView().findViewById<TabLayout>(R.id.tabLayoutCategorias)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when(tab.text){
                        "Top" -> return
                        "P.Magicos"-> openFragments(PueblosMagicosFragment())
                        "Restaurantes" -> openFragments(RestaurantsFragment())
                        "Hotles" -> return
                        "Playas" -> return
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })*/

        /*val datos:List<DataClassCategorias> = listOf(
            DataClassCategorias("Top",R.drawable.estrella64),
            DataClassCategorias("P.Magicos", R.drawable.rehilete),
            DataClassCategorias("Restaurantes", R.drawable.restaurante64),
            DataClassCategorias("Hoteles", R.drawable.hotel64)
        )

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)

        recyclerView.adapter = AdapterClassCategorias(datos, childFragmentManager)
        adapterClassCategorias = AdapterClassCategorias(datos, childFragmentManager)*/

    }


    private fun permisos() {
        val checkPermiso = ClassPermisos(requireContext()).checkPermision(
            android.Manifest.permission.ACCESS_FINE_LOCATION,1
        )
        if (checkPermiso != true) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            requireContext().startActivity(intent)
        }
    }

    private fun markOption() {
        val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_container_principal)
        if(currentFragment!=null)
        {
            val fragmentClassName = currentFragment.javaClass.simpleName
            when(fragmentClassName)
            {
                "RestaurantsFragment" -> bar_navigation_category.selectTab(bar_navigation_category.tabs[2],true)
                "HotelesFragment" -> bar_navigation_category.selectTab(bar_navigation_category.tabs[3],true)
            }
        }
    }

    fun openFragments(fragment: Fragment)
    {
        val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_container_principal)
        if(!fragment::class.java.isInstance(currentFragment))
        {
            val fragmentTransaction = childFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container_principal, fragment)
            fragmentTransaction?.commit()
        }
    }
}

