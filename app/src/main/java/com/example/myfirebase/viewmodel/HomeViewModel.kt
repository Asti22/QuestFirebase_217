package com.example.myfirebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.repositori.RepositorySiswa
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val siswa: List<Siswa>) : HomeUiState // Hapus default listOf() agar data wajib diisi
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repositorySiswa: RepositorySiswa) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            try {
                // Mengambil data dari repository
                val listSiswa = repositorySiswa.getDataSiswa()

                // Jika list kosong, bisa tetap Success(empty) atau arahkan ke state lain
                homeUiState = HomeUiState.Success(listSiswa)
            } catch (e: IOException) {
                homeUiState = HomeUiState.Error
            } catch (e: Exception) {
                homeUiState = HomeUiState.Error
            }
        }
    }
}