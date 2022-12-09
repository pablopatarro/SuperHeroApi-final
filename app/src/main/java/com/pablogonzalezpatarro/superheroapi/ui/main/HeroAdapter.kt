package com.pablogonzalezpatarro.superheroapi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pablogonzalezpatarro.superheroapi.Hero
import com.pablogonzalezpatarro.superheroapi.R
import com.pablogonzalezpatarro.superheroapi.databinding.ViewHeroBinding

class HeroAdapter (val listener: (Hero) -> Unit): RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

     var heroes = emptyList<Hero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_hero, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroes[position]
        holder.bind(hero)

        holder.itemView.setOnClickListener {
            listener(hero)
        }
    }

    override fun getItemCount(): Int = heroes.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ViewHeroBinding.bind(view)
        fun bind(hero: Hero) {
            //añadimos el id y nombre del héroe al textView correspondiente.
            binding.tvNombre.text = "ID: ${hero.id} - ${hero.nombre}"
        }
    }
}

