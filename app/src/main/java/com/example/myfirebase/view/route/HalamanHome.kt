package com.example.myfirebase.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirebase.R
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.view.route.DestinasiHome
import com.example.myfirebase.view.route.SiswaTopAppBar
import com.example.myfirebase.viewmodel.HomeUiState
import com.example.myfirebase.viewmodel.HomeViewModel
import com.example.myfirebase.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.loadSiswa()
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
    ) { innerPadding ->
        HomeBody(
            statusUiSiswa = viewModel.homeUiState,
            onSiswaClick = onDetailClick,
            retryAction = { viewModel.loadSiswa() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    statusUiSiswa: HomeUiState,
    onSiswaClick: (String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (statusUiSiswa) {
            is HomeUiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
            is HomeUiState.Success -> {
                if (statusUiSiswa.siswa.isEmpty()) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Tidak ada data siswa") }
                } else {
                    ListSiswaItem(
                        listSiswa = statusUiSiswa.siswa,
                        onSiswaClick = onSiswaClick
                    )
                }
            }
            is HomeUiState.Error -> OnError(retryAction = retryAction, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ListSiswaItem(
    listSiswa: List<Siswa>,
    onSiswaClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = listSiswa, key = { it.id }) { person ->
            CardSiswa(
                siswa = person,
                onSiswaClick = { onSiswaClick(person.id) },
                modifier = Modifier.fillMaxWidth() // Perbaikan: Kartu melebar penuh
            )
        }
    }
}

@Composable
fun CardSiswa(
    siswa: Siswa,
    onSiswaClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onSiswaClick(siswa.id) }, // Kunci navigasi
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f) // Perbaikan: Dorong ikon ke kanan
                )
                Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(text = siswa.telpon, style = MaterialTheme.typography.titleMedium)
            }
            Text(text = siswa.alamat, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Gagal memuat data")
        Button(onClick = retryAction) { Text("Coba Lagi") }
    }
}