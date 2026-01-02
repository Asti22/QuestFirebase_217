package com.example.myfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// Import tema Anda (biasanya ada di folder ui.theme)
import com.example.myfirebase.ui.theme.MyFirebaseTheme
import com.example.myfirebase.view.controllNavigasi.DataSiswaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Bungkus dengan Theme agar UI tidak error/berantakan
            MyFirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataSiswaApp()
                }
            }
        }
    }
}