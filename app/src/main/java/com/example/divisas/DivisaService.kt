package com.example.divisas

import retrofit2.http.GET
import retrofit2.http.Query

interface DivisaService {

    @GET("latest")
    suspend fun getConversion(
        @Query("apikey") apikey: String,
        @Query("base_currency") base_currency: String,
        @Query("currencies") currencies: String
    ) : ResultData

    @GET("currencies")
    suspend fun getDivisas(
        @Query("apikey") apikey: String
    ) : DivisaData
}