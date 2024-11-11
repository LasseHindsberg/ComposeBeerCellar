package com.example.composebeercellar.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.composebeercellar.model.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BeersRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"

    private val beersService: BeersService

    val BeersFlow: MutableState<List<Beer>> = mutableStateOf(listOf())
    val isLoading = mutableStateOf(false)
    val errorMessageFlow = mutableStateOf("")

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beersService = build.create(BeersService::class.java)
        getBeers()
    }

    fun getBeers() {
        isLoading.value = true
        beersService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("BANANA", response.body().toString())
                    val beerList: List<Beer>? = response.body()
                    BeersFlow.value = beerList ?: emptyList()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("Banana", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoading.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("Banana", message)
            }
        })
    }

    fun add(beer: Beer) {
        beersService.saveBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("BANANA", "Added: " + response.body())
                    getBeers()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("Banana", message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("Banana", message)
            }
        })
    }

    fun delete(id: Int) {
        Log.d("Banana", "Delete: $id")
        beersService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("BANANA", "Delete: " + response.body())
                    errorMessageFlow.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("Banana", "Not deleted: $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("Banana", "Not deleted $message")
            }
        })
    }

    fun update(beerId: Int, beer: Beer) {
        Log.d("BANANA", "Update: $beerId $beer")
        beersService.updateBeer(beerId, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("Banana", "Updated: " + response.body())
                    errorMessageFlow.value = ""
                    Log.d("Banana", "update successful")
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("Banana", "Update $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("Banana", "Update $message")
            }
        })
    }


    fun filterByName(nameFragment: String) {
        if (nameFragment.isEmpty()) {
            getBeers()
            return
        }
        BeersFlow.value =
            BeersFlow.value.filter {
                it.name.contains(nameFragment, ignoreCase = true)
            }
    }

    fun sortBeersByName(ascending: Boolean) {
        Log.d("Banana", "Sort By Name")
        if (ascending)
            BeersFlow.value = BeersFlow.value.sortedBy { it.name }
        else
            BeersFlow.value = BeersFlow.value.sortedByDescending { it.name }
    }

    fun sortBeersByABV(ascending: Boolean) {
        Log.d("Banana", "Sort By ABV")
        if (ascending)
            BeersFlow.value = BeersFlow.value.sortedBy { it.abv }
        else
            BeersFlow.value = BeersFlow.value.sortedByDescending { it.abv }
    }
}