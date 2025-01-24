package com.aplikasi.myapplication

data class BukuResponse(
    val id_buku: String,
    val judul: String,
    val pengarang: String,
    val editor: String?,
    val penerbit: String?,
    val ISBN: String,
    val tebal: String,
    val kategori: String,
    val deskripsi: String,
    val gambar_url: String,
    val pdf_url: String
)

