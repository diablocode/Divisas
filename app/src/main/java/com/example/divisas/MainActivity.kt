package com.example.divisas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.divisas.ui.theme.DivisasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivisasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MostrarConversion()
                }
            }
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MostrarConversion(modifier: Modifier = Modifier) {
        var contenidoWebService by remember { mutableStateOf("") }
        var divisasData by remember { mutableStateOf<DivisaData?>(null) }
        var divisaOrigen by remember { mutableStateOf("Selecciona Origen") }
        var divisaDestino by remember { mutableStateOf("Selecciona Destino") }
        var expandido by remember { mutableStateOf(false) }
        var botonPresionado by remember { mutableStateOf("") }

        var divisaViewModel = DivisaViewModel()
        val apiKey = "XXXXX"


        try {
            divisaViewModel.viewModelScope.launch(Dispatchers.IO){
                divisasData = divisaViewModel.
                    getDivisas(apiKey)
                Log.i("DIVISAS", divisasData.toString())
            }
        }
        catch (ex: Exception){

        }
        if(divisaOrigen.length == 3 && divisaDestino.length == 3) {
            divisaViewModel.viewModelScope.launch(Dispatchers.IO) {
                contenidoWebService = divisaViewModel.getConversion(
                    apiKey,
                    divisaOrigen,
                    divisaDestino
                ).data[divisaDestino].toString()
            }
        }
        Column {
            Button(onClick = {
                expandido = true
                botonPresionado = "botonOrigen"
            }) {
                Text(text = divisaOrigen)
            }

            Button(onClick = {
                expandido = true
                botonPresionado = "botonDestino"
            }) {
                Text(text = divisaDestino)
            }
            Text(
                text = contenidoWebService,
                modifier = modifier
            )
            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                divisasData?.data?.forEach{ divisaItem ->
                    DropdownMenuItem(
                        text = { Text(text = divisaItem.value.name )},
                        onClick = {
                            if(botonPresionado == "botonOrigen") {
                                divisaOrigen = divisaItem.value.code
                            }
                            if (botonPresionado == "botonDestino") {
                                divisaDestino = divisaItem.value.code
                            }
                            expandido = false
                        })
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DivisasTheme {
            MostrarConversion()
        }
    }

}