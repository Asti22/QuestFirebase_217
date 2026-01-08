package com.example.myfirebase.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myfirebase.repositori.RepositorySiswa

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
): ViewModel() {

    private val itemId: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var uiStateDetailSiswa by mutableStateOf(UIStateSiswa())
        private set

    init { getSiswaById() }


