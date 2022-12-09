package com.pablogonzalezpatarro.superheroapi.ui.main

import android.provider.Settings.Global.getString
import androidx.lifecycle.*
import com.pablogonzalezpatarro.superheroapi.Hero
import com.pablogonzalezpatarro.superheroapi.R
import com.pablogonzalezpatarro.superheroapi.model.RemoteConnection
import com.pablogonzalezpatarro.superheroapi.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state;

    //Iniciamos los valores definidos anteriormente.
    init{
         viewModelScope.launch (Dispatchers.Main){
             _state.value = _state.value?.copy(loading = true)

             //Pedimos la lista de todos los héroes.
             val result:Results = RemoteConnection.results.getAllHeroes("all.json")

             val allHeroes  = result.map {
                 Hero( it.id,
                     it.name,
                     it.appearance.gender,
                     it.images,
                     it.powerstats)
             }
             //Cuando llegamos aquí, cambiamos el state, pasandole la lista de héroes y false al loading.
             _state.value = _state.value?.copy(loading = false, heroes = allHeroes)
           }
        }


//Definimos dos funciones que nos van a facilitar la navegación al detail.
    fun navigateTo(hero: Hero) {
        _state.value = _state.value?.copy(navigateTo = hero)
    }

    fun onNavigateDone(){
        _state.value = _state.value?.copy(navigateTo = null)
    }

    //Creamos la clase UiState para controlar el estado de la navegación.
    data class UiState(
        val loading:Boolean = false,
        val heroes: List<Hero>? = null,
        val navigateTo: Hero? = null
        )
}

//Creamos la clase MainViewModelFactory
class MainViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}
