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
import android.widget.Toast
import com.example.tesbuatpdf.databinding.ActivityMainBinding
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.List
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Text
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

//            val pdfInputStream = resources.openRawResource(R.raw.format_skpi)
//
//            binding.pdfView.fromStream(pdfInputStream)
//                .defaultPage(0)
//                .load()

            binding.pdfView.fromFile(File(pdfFileName))
                .defaultPage(0) // Halaman pertama
                .load()
        }
    }

    private fun createPDF(){
        val pdfDocument = PdfDocument()
//        buatPDF1(pdfDocument)
//        buatPDF2(pdfDocument, this)
        // Simpan dokumen PDF ke penyimpanan
        file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")


        val writer = PdfWriter(file)

        val pdfDocument2 = com.itextpdf.kernel.pdf.PdfDocument(writer)
        val document = Document(pdfDocument2)
        FileOutputStream(file)

        val text1 = Text("Bold").setBold()
        val text2 = Text("Italic").setItalic()
        val text3 = Text("Underline").setUnderline()

        val paragraph = Paragraph("Hello World, Ini Bagas")

        val paragraph1 = Paragraph()
            .add(text1)
            .add(text2)
            .add(text3)

        val list = List()
            .add("Android")
            .add("Java")
            .add("C++")
            .add("Kotlin")

        document.add(paragraph)
        document.add(paragraph1)
        document.add(list)
        document.close()
        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show()

//        pdfDocument.writeTo(FileOutputStream(file))

        Log.d("PDF_PATH", file!!.absolutePath)

        // Tutup PdfDocument setelah selesai
        pdfDocument.close()

    }
}