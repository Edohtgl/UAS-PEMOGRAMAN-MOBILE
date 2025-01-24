package com.aplikasi.myapplication.menu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.myapplication.ApiService
import com.aplikasi.myapplication.BukuAdapterList
import com.aplikasi.myapplication.BukuResponse
import com.aplikasi.myapplication.R
import com.aplikasi.myapplication.activity.DetailBukuActivity
import com.aplikasi.myapplication.activity.Profil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadlistFragment : Fragment(R.layout.fragment_readlist) {

    private lateinit var recyclerViewBuku: RecyclerView
    private lateinit var bukuAdapter: BukuAdapterList
    private lateinit var bukuList: List<BukuResponse>
    private lateinit var cariJudul: EditText
    private lateinit var filterButton: ImageButton
    private lateinit var kategoriList: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profilBtn: ImageButton = view.findViewById(R.id.profil)
        recyclerViewBuku = view.findViewById(R.id.recyclerViewBuku)
        cariJudul = view.findViewById(R.id.cariJudul)
        filterButton = view.findViewById(R.id.filter)

        recyclerViewBuku.layoutManager = GridLayoutManager(requireContext(), 3)

        cariJudul.addTextChangedListener {
            val query = cariJudul.text.toString()
            if (query.isNotEmpty()) {
                searchBuku(query)
            }
        }

        profilBtn.setOnClickListener {
            val intent = Intent(requireContext(), Profil::class.java)
            startActivity(intent)
        }

        filterButton.setOnClickListener {
            showKategoriDialog()
        }

        getKategoriBuku()

        getDaftarBuku()
    }

    private fun getDaftarBuku() {
        ApiService.instance.getDaftarBuku().enqueue(object : Callback<List<BukuResponse>> {
            override fun onResponse(call: Call<List<BukuResponse>>, response: Response<List<BukuResponse>>) {
                if (response.isSuccessful) {
                    bukuList = response.body() ?: emptyList()

                    bukuAdapter = BukuAdapterList(requireContext(), bukuList)

                    recyclerViewBuku.adapter = bukuAdapter
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BukuResponse>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchBuku(query: String) {
        ApiService.instance.getBukuByQuery(query = query).enqueue(object : Callback<List<BukuResponse>> {
            override fun onResponse(call: Call<List<BukuResponse>>, response: Response<List<BukuResponse>>) {
                if (response.isSuccessful) {
                    bukuList = response.body() ?: emptyList()
                    bukuAdapter = BukuAdapterList(requireContext(), bukuList)
                    recyclerViewBuku.adapter = bukuAdapter
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BukuResponse>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterBukuByKategori(kategori: String) {
        ApiService.instance.getBukuByKategori(kategori = kategori).enqueue(object : Callback<List<BukuResponse>> {
            override fun onResponse(call: Call<List<BukuResponse>>, response: Response<List<BukuResponse>>) {
                if (response.isSuccessful) {
                    bukuList = response.body() ?: emptyList()
                    bukuAdapter = BukuAdapterList(requireContext(), bukuList)
                    recyclerViewBuku.adapter = bukuAdapter
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BukuResponse>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getKategoriBuku() {
        ApiService.instance.getKategoriBuku().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    kategoriList = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showKategoriDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Kategori Buku")

        val kategoriDenganSemua = listOf("Semua") + kategoriList
        val kategoriArray = kategoriDenganSemua.toTypedArray()

        builder.setItems(kategoriArray) { _, which ->
            val selectedKategori = kategoriArray[which]

            if (selectedKategori == "Semua") {
                getDaftarBuku()
            } else {
                filterBukuByKategori(selectedKategori)
            }
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

}
