package com.pablogonzalezpatarro.superheroapi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pablogonzalezpatarro.superheroapi.Hero
import com.pablogonzalezpatarro.superheroapi.R
import com.pablogonzalezpatarro.superheroapi.databinding.FragmentMainBinding
import com.pablogonzalezpatarro.superheroapi.ui.detail.DetailFragment
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels{ MainViewModelFactory()}
    private lateinit var binding: FragmentMainBinding
    private val adapter = HeroAdapter(){ hero -> viewModel.navigateTo(hero)}
    //declaramos una nueva lista, que es la que vamos a usar para filtrar los personajes.
    private var listaFiltrada: List<Hero>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        //Ponemos el nombre de la aplicación en la actionBar.
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        //Añadimos un evento en el componente de búsqueda (searchView)
        val busqueda = binding.svHero
        busqueda.clearFocus()

        viewModel.state.observe(viewLifecycleOwner){state->
            //Mostramos la barra de progreso en función del state.
            binding.progress.visibility = if(state.loading) VISIBLE else GONE

            //Mientras la lista de héroes no sea nula...
            state.heroes?.let {
                //... se la mandamos al adapter
                adapter.heroes = state.heroes
                adapter.notifyDataSetChanged()
            }

            //La zona donde añadimos el evento.
            // Una vez que tenemos la lista de héroes, ponemos el evento en la barra de búsqueda.
            //Cada vez que se realiza un cambio en la barra,se lanza un evento que nos filtra los
            // héroes cuyo nombre contiene el texto que había en la barra.
            // Filtramos dichos héroes en una lista y los mandamos al adapter.
            busqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(texto: String?): Boolean {
                    listaFiltrada = emptyList<Hero>()
                    val textoABuscar = texto?.lowercase()
                    if (textoABuscar?.isNotEmpty() == true){
                        //Si el texto que había en el searchView no es vacío, se filtra.
                        listaFiltrada = state.heroes?.filter {
                            //El criterio de filtrado es si el nombre del héroe contiene o no
                            // el texto que introduce el usuario
                            it.nombre?.lowercase()?.contains(textoABuscar) == true
                        }
                    }
                    else {
                        //Si la barra de búsqueda recoje un texto vacío, la lista filtrada debe ser
                        // la lista original.
                        listaFiltrada = state.heroes!!
                    }
                    //Le mandamos al adapter la lista filtrada.
                    adapter.heroes = listaFiltrada!!
                    adapter?.notifyDataSetChanged()

                    //Ni idea de por qué devuelve false.
                    return false
                }
            })  //Fin del evento.

            //Definimos la navegación.
            state.navigateTo?.let {
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragment,
                    bundleOf(DetailFragment.EXTRA_HERO to it)
                )
                //cambiamos el estado de la navegación a null.
                viewModel.onNavigateDone()
            }

        }

    }//Fin de onViewCreated.

}//Fin del mainFragment.



