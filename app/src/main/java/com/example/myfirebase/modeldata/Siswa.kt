package com.example.myfirebase.modeldata

data class Siswa(
    // Diubah dari Long ke String agar cocok dengan document.id Firestore
    val id: String = "",
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

data class DetailSiswa(
    // Harus String juga agar sinkron dengan class Siswa
    val id: String = "",
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

/* Fungsi pemetaan (Mapper) */
fun DetailSiswa.toDataSiswa(): Siswa = Siswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun Siswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

fun Siswa.toUiStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)