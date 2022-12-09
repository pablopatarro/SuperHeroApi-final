package com.pablogonzalezpatarro.superheroapi.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.pablogonzalezpatarro.superheroapi.Hero
import com.pablogonzalezpatarro.superheroapi.R
import com.pablogonzalezpatarro.superheroapi.databinding.FragmentDetailBinding
import java.util.*


//Vamos a hacer que el detailFragmen implemente una interfaz que nos ayudará con uno de los
// componentes que vamos a utilizar; el Spinner.

class DetailFragment : Fragment(R.layout.fragment_detail) ,OnItemSelectedListener{
    private lateinit var mapaEstadisticas: Map<String, Int?>
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(arguments?.getParcelable<Hero>(EXTRA_HERO)!!)
    }

    //Parte estática de la clase.
    companion object{
        const val EXTRA_HERO = "DetailActivity:hero"
        //Definimos la lista de las posibles estadísticas que tiene un Hero.
         val estadisticas = Arrays.asList("Seleccione una estadística",
            "Fuerza","Velocidad","Inteligencia","Poder","Combate","Durabilidad")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        //Definimos el array que vamos a mandar al adapter del spinner.
        val arrayStats = this.context?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_spinner_dropdown_item)
        }

        viewModel.hero.observe(viewLifecycleOwner){ hero->
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "ID: ${hero.id}"

            //Una vez que tenemos el Hero, hacemos un mapa con
            // la palabra correspondiente y el valor de la estadística.
            mapaEstadisticas = mapa(hero)

            //Asignamos el array de estadisticas del companion object a la variable arrayStats,
            // solo cuando esta sea no nula..
            if (arrayStats != null) {
                arrayStats.addAll(estadisticas)
            }
            //Evento de selección. Le decimos al spinner quién escucha el evento.
            // Es this porque es la clase detailFragment la que implementa los métodos de la interfaz.
            binding.spinner.onItemSelectedListener = this
            //Llegado a este punto, le mandamos la lista de string al adapter del spinner.
            binding.spinner.adapter = arrayStats

            //Asignamos a cada uno de los campos del detail su correspondiente
            // valor de la variable hero.
            binding.tvDetailNombre.text = "Nombre del personaje: ${hero.nombre}"

            //Asignamos una variable para determinar el género en español.
            var genero="Indefinido"
            if (hero.genero=="Male")
                genero = "Masculino"
            else if (hero.genero == "Female")
                genero = "Femenino"

            binding.tvDetailGenero.text = "Género del personaje: $genero"
            //Asignamos el campo que muestra las estadísticas en blanco.
            //Se cambiará con el evento.
            binding.tvDetailStat.text = ""

            //Habría que sacar esto en una función.
            Glide.with(binding.imagen)
                .load(hero.imagen?.lg)
                .into(binding.imagen)
        }
    }



    //MÉTODOS DE LA INTERFAZ.
    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        //Dependiendo del item seleccionado, debemos mostrar algo distinto en el textView
        // de las estadísticas.
        //Si la posición del item elegido es mayor que cero (no es el primer elemento que se muestra)
        // entonces, mostramos una cadena de texto con la estadística que se ha elegido.
        if(position>0)
           binding.tvDetailStat.text = cadenaAMostrar(position)
        else
            //En otro caso, se limpia el campo donde se muestra la estadística.
            binding.tvDetailStat.text =""
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Este método lo dejamos vacío porque siempre va a haber item seleccionado.
    }

    /*********************FUNCIONES***************************/

    private fun mapa(hero:Hero): Map<String, Int?> {
        return mapOf("Fuerza" to  hero.estadisticas?.strength,
            "Velocidad" to hero.estadisticas?.speed,
            "Inteligencia" to hero.estadisticas?.intelligence,
            "Poder" to hero.estadisticas?.power,
            "Combate" to hero.estadisticas?.combat,
            "Durabilidad" to hero.estadisticas?.durability)
    }

    private fun cadenaAMostrar(position: Int): String {
        //Guardamos en una variable el string de la estadística elegida.
        val estadistica = estadisticas[position]
        //Retornamos la cadena siguiente.
        return "La estadística de $estadistica es: ${mapaEstadisticas[estadistica]}"
    }

}
