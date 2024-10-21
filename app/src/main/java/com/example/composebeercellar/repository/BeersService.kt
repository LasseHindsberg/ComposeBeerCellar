package com.example.composebeercellar.repository

// Imports
import com.example.composebeercellar.model.Beer
import retrofit2.Call
import retrofit2.http.*


interface BeersService {
    // Get All
    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>


    // Get by Id
    @GET("beers/{BeerId}")
    fun GetBeerById(@Path("BeerId") BeerId: Int): Call<Beer>

    // POST
    @POST("beers")
    fun saveBeer(@Body beer: Beer): Call<Beer>

    // Remove
    @DELETE("beers/{id}")
    fun deleteBeer(@Path("id") id: Int): Call<Beer>

    // UPDATE - Not used currently
    // @PUT("beers/{id}")
    // fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Call<Beer>

}