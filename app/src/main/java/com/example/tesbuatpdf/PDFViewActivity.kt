package com.example.tesbuatpdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tesbuatpdf.databinding.ActivityPdfviewBinding
import java.io.File

class PDFViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfviewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val file = File(this@PDFViewActivity.getExternalFilesDir(null), "dokumen.pdf")
        val pdfFileName = file?.absolutePath // Gunakan objek File yang telah Anda buat sebelumnya

        Log.d("PDF_PATH", file!!.absolutePath)
        if (pdfFileName != null) {
            Log.d("File Name", pdfFileName)
        }
        binding.pdfView.fromFile(File(pdfFileName))
            .defaultPage(0) // Halaman pertama
            .load()

    }
}