package com.aplikasi.myapplication.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.myapplication.BukuSimpan
import com.aplikasi.myapplication.DownloadsAdapter
import com.aplikasi.myapplication.R
import com.aplikasi.myapplication.activity.DetailBukuActivity
import com.aplikasi.myapplication.activity.Profil
import com.aplikasi.myapplication.databinding.FragmentDownloadsBinding
import com.google.gson.Gson

class DownloadsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DownloadsAdapter
    private var bukuList: List<BukuSimpan> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_downloads, container, false)
        val profilBtn: ImageButton = rootView.findViewById(R.id.profil)

        recyclerView = rootView.findViewById(R.id.recyclerViewBuku)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        adapter = DownloadsAdapter()
        recyclerView.adapter = adapter

        loadSavedBooks()

        val searchButton: ImageButton = rootView.findViewById(R.id.search)
        searchButton.setOnClickListener {
            showSearchDialog()
        }

        profilBtn.setOnClickListener {
            val intent = Intent(requireContext(), Profil::class.java)
            startActivity(intent)
        }

        return rootView
    }

    private fun loadSavedBooks() {
        val sharedPreferences = activity?.getSharedPreferences("BukuSimpan", Context.MODE_PRIVATE)
        val gson = Gson()
        val bukuListJson = sharedPreferences?.getString("buku_list", "[]")
        bukuList = gson.fromJson(bukuListJson, Array<BukuSimpan>::class.java).toList()
        adapter.submitList(bukuList)
    }

    private fun showSearchDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_search, null)
        val searchEditText: EditText = dialogView.findViewById(R.id.searchEditText)

        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Cari Buku")
            .setView(dialogView)
            .setPositiveButton("Cari") { _, _ ->
                val searchText = searchEditText.text.toString().trim()
                if (searchText.isNotEmpty()) {
                    searchBooks(searchText)
                } else {
                    Toast.makeText(requireContext(), "Nyari apa bg?", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()

        dialog.show()
    }

    private fun searchBooks(query: String) {
        val filteredList = bukuList.filter { it.judul.contains(query, ignoreCase = true) }
        adapter.submitList(filteredList)
    }
}
