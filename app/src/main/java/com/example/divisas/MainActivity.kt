package com.example.divisas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.divisas.ui.theme.DivisasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        var divisaDestino by remember { mutableStateOf("") }
        var expandido by remember { mutableStateOf(false) }


        var divisaViewModel = DivisaViewModel()
        val apiKey = "XXXXX"
        val baseCurrency = "MXN"
        val currencies = "USD"

        divisaViewModel.viewModelScope.launch(Dispatchers.IO){
            divisasData = divisaViewModel.
                getDivisas(apiKey)
            Log.i("DIVISAS", divisasData.toString())
        }
        divisaViewModel.viewModelScope.launch(Dispatchers.IO){
            contenidoWebService = divisaViewModel.
                getConversion(apiKey, baseCurrency, currencies).
                data[currencies].toString()
        }
        Column {
            Button(onClick = { expandido = true }) {
                Text(text = divisaOrigen)
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Selecciona Destino")
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
                            divisaOrigen = divisaItem.value.code
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