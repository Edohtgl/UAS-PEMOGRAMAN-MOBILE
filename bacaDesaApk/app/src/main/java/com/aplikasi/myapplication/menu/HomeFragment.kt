package com.aplikasi.myapplication.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.myapplication.BukuResponse
import com.aplikasi.myapplication.R
import com.aplikasi.myapplication.ApiService
import com.aplikasi.myapplication.BukuAdapter
import com.aplikasi.myapplication.activity.BacaBukuActivity
import com.aplikasi.myapplication.activity.DetailBukuActivity
import com.aplikasi.myapplication.activity.Profil
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bukuAdapter: BukuAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bacaSekarang: Button = view.findViewById(R.id.bacaNow)
        val profilBtn: ImageButton = view.findViewById(R.id.profil)
        val latestBookImage: ImageView = view.findViewById(R.id.gambarterbaru)
        recyclerView = view.findViewById(R.id.recyclerViewRekomendasi)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        ApiService.instance.getLatestBook().enqueue(object : Callback<BukuResponse> {
            override fun onResponse(call: Call<BukuResponse>, response: Response<BukuResponse>) {
                if (response.isSuccessful) {
                    val book = response.body()
                    if (book != null) {

                        Glide.with(requireContext()).load(book.gambar_url).into(latestBookImage)

                        latestBookImage.setOnClickListener {
                            val intent = Intent(requireContext(), DetailBukuActivity::class.java)
                            intent.putExtra("book_id", book.id_buku) // Kirimkan ID Buku
                            startActivity(intent)
                        }


                        bacaSekarang.setOnClickListener {
                            val intent = Intent(requireContext(), BacaBukuActivity::class.java)
                            intent.putExtra("pdf_url", book.pdf_url)
                            startActivity(intent)
                        }

                        profilBtn.setOnClickListener {
                            val intent = Intent(requireContext(), Profil::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BukuResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal mengambil data terbaru", Toast.LENGTH_SHORT).show()
            }
        })


        ApiService.instance.getBookRecommendations().enqueue(object : Callback<List<BukuResponse>> {
            override fun onResponse(call: Call<List<BukuResponse>>, response: Response<List<BukuResponse>>) {
                if (response.isSuccessful) {
                    val books = response.body()
                    if (!books.isNullOrEmpty()) {
                        bukuAdapter = BukuAdapter(books) { book ->
                            val intent = Intent(requireContext(), DetailBukuActivity::class.java)
                            intent.putExtra("book_id", book.id_buku)
                            startActivity(intent)
                        }
                        recyclerView.adapter = bukuAdapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat rekomendasi buku", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BukuResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal mengambil rekomendasi", Toast.LENGTH_SHORT).show()
            }
        })


    }
}
