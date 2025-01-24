package com.aplikasi.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.myapplication.activity.BacaBukuActivity
import com.bumptech.glide.Glide
import java.io.File

class DownloadsAdapter : ListAdapter<BukuSimpan, DownloadsAdapter.BukuViewHolder>(BukuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = getItem(position)
        holder.bind(buku)
    }

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gambarDownload: ImageView = itemView.findViewById(R.id.gambarDownload)
        private val judulDownload: TextView = itemView.findViewById(R.id.judulDownload)
        private val kategori: TextView = itemView.findViewById(R.id.kategori)
        private val downloadButton: CardView = itemView.findViewById(R.id.bacaYa)

        fun bind(buku: BukuSimpan) {
            Glide.with(itemView.context).load(buku.gambar_url).into(gambarDownload)
            judulDownload.text = buku.judul
            kategori.text = buku.pengarang

            downloadButton.setOnClickListener {
                val intent = Intent(itemView.context, BacaBukuActivity::class.java)
                intent.putExtra("pdf_url", buku.pdf_url)
                itemView.context.startActivity(intent)
            }
        }
    }

    class BukuDiffCallback : DiffUtil.ItemCallback<BukuSimpan>() {
        override fun areItemsTheSame(oldItem: BukuSimpan, newItem: BukuSimpan): Boolean {
            return oldItem.id_buku == newItem.id_buku
        }

        override fun areContentsTheSame(oldItem: BukuSimpan, newItem: BukuSimpan): Boolean {
            return oldItem == newItem
        }
    }
}
