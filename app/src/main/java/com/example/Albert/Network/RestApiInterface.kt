package com.example.Albert.Network

import com.example.Albert.Network.Models.BookListingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiInterface {

    @GET("search.json")
    fun getSearchResults(@Query("q") token:String): Call<BookListingResponse>

    @GET("search.json")
    fun getSearchResults(@Query("q") token:String, @Query("page") page:Int): Call<BookListingResponse>


}