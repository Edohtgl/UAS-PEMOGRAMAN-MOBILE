package com.aplikasi.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.myapplication.activity.DetailBukuActivity
import com.bumptech.glide.Glide

class BukuAdapterList(private val context: Context, private val bukuList: List<BukuResponse>) : RecyclerView.Adapter<BukuAdapterList.BukuViewHolder>() {

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gambarBuku: ImageView = itemView.findViewById(R.id.gambarBuku)
        val judulBuku: TextView = itemView.findViewById(R.id.judulBuku)

        init {

            gambarBuku.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val buku = bukuList[position]
                    val intent = Intent(context, DetailBukuActivity::class.java)
                    intent.putExtra("book_id", buku.id_buku)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bukulist, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = bukuList[position]
        holder.judulBuku.text = buku.judul
        Glide.with(context).load(buku.gambar_url).into(holder.gambarBuku)
    }

    override fun getItemCount(): Int = bukuList.size
}
