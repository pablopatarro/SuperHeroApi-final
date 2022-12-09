package com.pablogonzalezpatarro.superheroapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pablogonzalezpatarro.superheroapi.Hero

class DetailViewModel (hero: Hero): ViewModel(){
    private val _hero = MutableLiveData(hero)
    val hero: LiveData<Hero> get() = _hero

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val hero: Hero): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(hero) as T
    }

}