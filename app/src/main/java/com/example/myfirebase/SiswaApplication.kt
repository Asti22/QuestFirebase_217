package com.example.myfirebase

import android.app.Application
import com.example.myfirebase.repositori.ContainerApp
import com.example.myfirebase.repositori.DefaultContainerApp

class SiswaApplication : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Ganti RepositorySiswa() dengan DefaultContainerApp()
        container = DefaultContainerApp()
    }
}