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
                    val BeerList: List<Beer>? = response.body()
                    BeersFlow.value = BeerList ?: emptyList()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoading.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
            }
        })
    }
    fun add(beer: Beer) {
        beersService.saveBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    getBeers()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
            }
        })
    }

    fun delete(id: Int) {
        beersService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("BANANA", "Delete: " + response.body())
                    errorMessageFlow.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
            }
        })
    }



}