package com.example.composebeercellar.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.composebeercellar.repository.BeersRepository

class BeersViewModel : ViewModel() {
private val repository = BeersRepository()
    val beersFlow: State<List<Beer>> = repository.BeersFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow

    val reloadingFlow: State<Boolean> = repository.isLoading

    init {
        reload()
    }

    fun reload() {
        repository.getBeers()
    }

    fun add(beer: Beer) {
        repository.add(beer)
    }

    fun delete(beer: Beer) {
        repository.delete(beer.id)
    }
    fun update(id: Int, updatedBeer: Beer) {
        repository.update(id, updatedBeer)
    }

    fun filterByName(nameFragment: String) {
        repository.filterByName(nameFragment)
    }

    fun sortBeersByName(ascending: Boolean) {
        repository.sortBeersByName(ascending)
    }
    fun sortBeersByABV(ascending: Boolean) {
        repository.sortBeersByABV(ascending)
    }

}