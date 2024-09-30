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

    private fun reload() {
        repository.getBeers()
    }

    private fun add(beer: Beer) {
        repository.add(beer)
    }

    private fun delete(beer: Beer) {
        repository.delete(beer.id)
    }
}