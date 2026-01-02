package com.example.myfirebase.view.route

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirebase.ui.viewmodel.EditViewModel
import com.example.myfirebase.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSiswaScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope() // Digunakan untuk menjalankan fungsi suspend
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Mengatur perilaku scroll pada TopAppBar

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Menghubungkan scroll body dengan TopBar
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEdit.titleRes), // Menggunakan judul dari objek DestinasiEdit
                canNavigateBack = true, // Menampilkan tombol kembali
                navigateUp = onNavigateUp, // Aksi saat tombol kembali ditekan
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa, // Mengambil state data dari EditViewModel
            onSiswaValueChange = viewModel::updateUiState, // Fungsi untuk update data di UI
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSiswa() // Memanggil fungsi update di Firebase
                    navigateBack() // Kembali ke layar sebelumnya setelah sukses
                }
            },
            modifier = Modifier
                .padding(innerPadding) // Menghindari konten tertutup TopBar
                .verticalScroll(rememberScrollState()) // Memungkinkan form di-scroll
                .fillMaxWidth()
        )
    }
}