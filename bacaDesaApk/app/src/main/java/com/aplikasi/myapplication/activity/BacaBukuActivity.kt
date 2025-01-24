package com.aplikasi.myapplication.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aplikasi.myapplication.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BacaBukuActivity : AppCompatActivity(), OnPageChangeListener {

    private lateinit var pdfView: PDFView
    private lateinit var progressDialog: ProgressDialog
    private lateinit var pageNumberTextView: TextView
    private val client = OkHttpClient()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baca_buku)

        pdfView = findViewById(R.id.pdfView)
        pageNumberTextView = findViewById(R.id.pageNumberTextView)

        val pdfUrl = intent.getStringExtra("pdf_url")

        if (pdfUrl != null) {
            downloadPdf(pdfUrl)
        } else {
            Toast.makeText(this, "URL PDF tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadPdf(url: String) {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mengunduh buku...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    progressDialog.dismiss()
                    Toast.makeText(this@BacaBukuActivity, "Gagal mengunduh PDF", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val inputStream = response.body?.byteStream()
                val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "buku_terbaru.pdf")
                val outputStream = FileOutputStream(file)

                inputStream?.copyTo(outputStream)
                outputStream.close()
                inputStream?.close()

                runOnUiThread {
                    progressDialog.dismiss()
                    pdfView.fromFile(file)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onPageChange(this@BacaBukuActivity) // Menambahkan listener perubahan halaman
                        .load()
                }
            }
        })
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        currentPage = page + 1 // Halaman dimulai dari 0, maka ditambahkan 1
        pageNumberTextView.text = "Halaman $currentPage dari $pageCount"
    }
}
