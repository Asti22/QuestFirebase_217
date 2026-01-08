package com.example.myfirebase.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.myfirebase.repositori.RepositorySiswa

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
)