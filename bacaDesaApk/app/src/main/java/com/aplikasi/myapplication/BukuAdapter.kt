package com.aplikasi.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.aplikasi.myapplication.BukuResponse
import com.aplikasi.myapplication.R

class BukuAdapter(
    private val books: List<BukuResponse>,
    private val onBookClick: (BukuResponse) -> Unit
) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_buku, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val book = books[position]
        Glide.with(holder.itemView.context).load(book.gambar_url).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onBookClick(book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.bukuRekomendasiItem)
    }
}
