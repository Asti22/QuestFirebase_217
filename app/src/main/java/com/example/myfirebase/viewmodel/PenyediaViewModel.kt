package com.example.myfirebase.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfirebase.SiswaApplication
import com.example.myfirebase.ui.viewmodel.DetailViewModel
import com.example.myfirebase.ui.viewmodel.EditViewModel

// Pastikan import ini sesuai dengan folder yang ada di screenshot kamu
// Jika semua file ada di folder yang sama, import ini mungkin tidak diperlukan
// atau cukup gunakan:
// import com.example.myfirebase.viewmodel.HomeViewModel

fun CreationExtras.siswaApplication(): SiswaApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SiswaApplication
        )

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Initializer untuk HomeViewModel
        initializer {
            HomeViewModel(siswaApplication().container.repositorySiswa)
        }

        // Initializer untuk EntryViewModel
        initializer {
            EntryViewModel(siswaApplication().container.repositorySiswa)
        }
        initializer { EditViewModel(savedStateHandle = this.createSavedStateHandle(),
            repositorySiswa = siswaApplication().container.repositorySiswa) }

        //
        initializer {
            DetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repositorySiswa = siswaApplication().container.repositorySiswa
            )
        }

    }
}