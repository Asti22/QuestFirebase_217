package com.example.myfirebase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.DetailSiswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.modeldata.toDataSiswa
import com.example.myfirebase.modeldata.toDetailSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    // Menyimpan state UI untuk halaman edit
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // Mengambil ID siswa dari parameter navigasi
    private val itemId: String = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        // Mengambil data siswa saat ini dari database untuk ditampilkan di form
        viewModelScope.launch {
            val siswa = repositorySiswa.getSiswaById(itemId)
            // Mengasumsikan ada fungsi toDetailSiswa() untuk konversi model
            uiStateSiswa = UIStateSiswa(detailSiswa = siswa.toDetailSiswa(), isEntryValid = true)
        }
    }

    /* Fungsi untuk memvalidasi input (sama dengan EntryViewModel) */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // Memperbarui state saat pengguna mengetik di form
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /* Fungsi untuk memperbarui data di Firebase */
    suspend fun updateSiswa() {
        if (validasiInput()) {
            // Menggunakan fungsi update pada repository
            repositorySiswa.updateSiswa(itemId, uiStateSiswa.detailSiswa.toDataSiswa())
        }
    }
}