package com.example.divisas

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class DivisaViewModel:ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.freecurrencyapi.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(DivisaService::class.java)

    suspend fun getConversion(
        apikey: String,
        base_currency: String,
        currencies: String
    ) : ResultData {
        val respuesta = service.getConversion(apikey,base_currency,currencies)
        return respuesta
    }

    suspend fun getDivisas(
        apikey: String
    ) : DivisaData {
        val respuesta = service.getDivisas(apikey)
        return respuesta
    }
}