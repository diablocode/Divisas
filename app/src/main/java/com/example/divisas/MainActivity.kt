package com.example.divisas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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

        var divisaViewModel = DivisaViewModel()
        val apiKey = "fca_live_Dox4LM8NuMBMdBqvukXIpp7e42XJUgrtKzFpQ80b"
        val baseCurrency = "MXN"
        val currencies = "USD"

        divisaViewModel.viewModelScope.launch(Dispatchers.IO){
            contenidoWebService = divisaViewModel.
                getConversion(apiKey, baseCurrency, currencies).
                data[currencies].toString()
        }

        Text(
            text = contenidoWebService,
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DivisasTheme {
            MostrarConversion()
        }
    }

}