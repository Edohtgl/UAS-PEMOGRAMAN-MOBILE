package com.aplikasi.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aplikasi.myapplication.ApiService
import com.aplikasi.myapplication.BukuResponse
import com.aplikasi.myapplication.BukuSimpan
import com.aplikasi.myapplication.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class DetailBukuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_buku)

        val bookId = intent.getStringExtra("book_id")
        Log.e("DetailBukuActivity", "$bookId")
        ApiService.instance.getBookDetails(bookId = bookId ?: "").enqueue(object : Callback<BukuResponse> {
            override fun onResponse(call: Call<BukuResponse>, response: Response<BukuResponse>) {
                if (response.isSuccessful) {
                    val book = response.body()
                    if (book != null) {

                        val gambarDetail: ImageView = findViewById(R.id.gambarDetail)
                        val judulBuku: TextView = findViewById(R.id.judulBuku)
                        val dekripsiBuku: TextView = findViewById(R.id.dekripsiBuku)
                        val judulBukuDeskripsi: TextView = findViewById(R.id.judulBukuDeskripsi)
                        val pengarangDeskripsi: TextView = findViewById(R.id.PengarangDeskripsi)
                        val penerbitDeskripsi: TextView = findViewById(R.id.penerbitDeskripsi)
                        val isbnDeskripsi: TextView = findViewById(R.id.isbnDeskripsi)
                        val tebalDeskripsi: TextView = findViewById(R.id.tebalDeskripsi)
                        Glide.with(this@DetailBukuActivity).load(book.gambar_url).into(gambarDetail)
                        judulBuku.text = book.judul
                        dekripsiBuku.text = book.deskripsi
                        judulBukuDeskripsi.text = book.judul
                        pengarangDeskripsi.text = book.pengarang
                        penerbitDeskripsi.text = book.penerbit
                        isbnDeskripsi.text = book.ISBN
                        tebalDeskripsi.text = book.tebal

                        val bacaSekarang: Button = findViewById(R.id.bacaSekarang)
                        bacaSekarang.setOnClickListener {
                            val intent = Intent(this@DetailBukuActivity, BacaBukuActivity::class.java)
                            intent.putExtra("pdf_url", book.pdf_url)
                            startActivity(intent)
                        }
                        val downloadButton: ImageButton = findViewById(R.id.downloadPdf)
                        downloadButton.setOnClickListener {
                            val bukuSimpan = BukuSimpan(
                                id_buku = book.id_buku,
                                judul = book.judul,
                                pengarang = book.pengarang,
                                gambar_url = book.gambar_url,
                                pdf_url = book.pdf_url
                            )
                            simpanBukuKeSharedPreferences(bukuSimpan)
                        }

                    }

                }




            }

            override fun onFailure(call: Call<BukuResponse>, t: Throwable) {
                Toast.makeText(this@DetailBukuActivity, "Gagal mengambil detail buku", Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun simpanBukuKeSharedPreferences(bukuSimpan: BukuSimpan) {
        val sharedPreferences = getSharedPreferences("BukuSimpan", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val bukuListJson = sharedPreferences.getString("buku_list", "[]")
        val bukuList = gson.fromJson(bukuListJson, Array<BukuSimpan>::class.java).toMutableList()
        bukuList.add(bukuSimpan)
        val updatedListJson = gson.toJson(bukuList)
        editor.putString("buku_list", updatedListJson)
        editor.apply()
    }
}
