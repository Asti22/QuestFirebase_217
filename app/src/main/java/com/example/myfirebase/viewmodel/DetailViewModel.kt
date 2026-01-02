package com.example.myfirebase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.toDetailSiswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiDetail
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    // PERBAIKAN: Gunakan DestinasiDetail.itemIdArg agar sinkron dengan HostNavigasi
    private val itemId: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var uiStateDetailSiswa by mutableStateOf(UIStateSiswa())
        private set

    init { getSiswaById() }

    fun getSiswaById() {
        viewModelScope.launch {
            val siswa = repositorySiswa.getSiswaById(itemId)
            uiStateDetailSiswa = uiStateDetailSiswa.copy(detailSiswa = siswa.toDetailSiswa())
        }
    }

    suspend fun deleteSiswa() { repositorySiswa.deleteSiswa(itemId) }
}