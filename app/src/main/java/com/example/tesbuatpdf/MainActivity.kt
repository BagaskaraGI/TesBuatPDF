package com.example.tesbuatpdf

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

//        val drawableId = R.drawable.disdik_bekasi
//        val bitmap = BitmapFactory.decodeResource(resources, drawableId)
//        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false)



        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnCreatePDF.setOnClickListener {
            createPDF()
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




    private fun createPDF(){
        val pdfDocument = PdfDocument()


//        buatPDF1(pdfDocument)

        buatPDF2(pdfDocument, this)

        // Simpan dokumen PDF ke penyimpanan
        file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")
        pdfDocument.writeTo(FileOutputStream(file))

        Log.d("PDF_PATH", file!!.absolutePath)

        // Tutup PdfDocument setelah selesai
        pdfDocument.close()

    }
}