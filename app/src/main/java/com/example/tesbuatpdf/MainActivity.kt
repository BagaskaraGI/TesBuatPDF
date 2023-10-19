package com.example.tesbuatpdf

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.example.tesbuatpdf.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var file : File? = null
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnCreatePDF.setOnClickListener {
            val pdfDocument = PdfDocument()

            val pageInfo = PdfDocument.PageInfo.Builder(300, 500, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            val paint = Paint()

            // Atur ukuran font dan warna teks
            paint.textSize = 12f
            paint.color = Color.BLACK

            canvas.drawText("TES BUAT PDF BAGAS", 40f, 50f, paint)

            // Selesai dengan halaman
            pdfDocument.finishPage(page)

            // Simpan dokumen PDF ke penyimpanan
            file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")
            pdfDocument.writeTo(FileOutputStream(file))

            Log.d("PDF_PATH", file!!.absolutePath)

            // Tutup PdfDocument setelah selesai
            pdfDocument.close()

        }

        binding.btnShowPDF.setOnClickListener {
            file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")
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
}