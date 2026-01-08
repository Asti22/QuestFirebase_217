package com.example.myfirebase.repositori

import com.example.myfirebase.modeldata.Siswa
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// INTERFACE
interface RepositorySiswa {
    suspend fun getDataSiswa(): List<Siswa>
    suspend fun postDataSiswa(siswa: Siswa)
    suspend fun getSiswaById(id: String): Siswa
    suspend fun deleteSiswa(id: String)
    suspend fun updateSiswa(id: String, siswa: Siswa)
}

// IMPLEMENTASI
class FirebaseRepositorySiswa : RepositorySiswa {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("Siswa")

    override suspend fun getDataSiswa(): List<Siswa> {
        return try {
            // Harus sama dengan nama koleksi di Firebase (misal: "Siswa")
            val snapshot = db.collection("Siswa").get().await()
            snapshot.documents.map { doc ->
                Siswa(
                    id = doc.id,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList() // Jika error, dia akan return list kosong
        }
    }

    override suspend fun postDataSiswa(siswa: Siswa) {
        try {
            val data = hashMapOf(
                "nama" to siswa.nama,
                "alamat" to siswa.alamat,
                "telpon" to siswa.telpon
            )
            collection.add(data).await()
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun getSiswaById(id: String): Siswa {
        return try {
            val document = collection.document(id).get().await()
            Siswa(
                id = document.id,
                nama = document.getString("nama") ?: "",
                alamat = document.getString("alamat") ?: "",
                telpon = document.getString("telpon") ?: ""
            )
        } catch (e: Exception) {
            Siswa(id = "", nama = "", alamat = "", telpon = "")
        }
    }
    override suspend fun deleteSiswa(id: String) {
        try { collection.document(id).delete().await() } catch (e: Exception) { throw e }
    }



}