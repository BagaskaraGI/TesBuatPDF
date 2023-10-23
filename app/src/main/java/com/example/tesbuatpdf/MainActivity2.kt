package com.example.tesbuatpdf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tesbuatpdf.databinding.ActivityMain2Binding
import java.io.File
import java.io.FileOutputStream

class MainActivity2 : AppCompatActivity() {

    private var file: File? = null
    private lateinit var binding: ActivityMain2Binding
    val pageWidth = 1200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreatePDF2.setOnClickListener {
            createPDF()
        }


    }


    private fun createPDF() {

        val nama = binding.etNama.text.toString()
        val telp = binding.etNoTelp.text.toString()
        val barang1 = binding.etBarang1.text.toString()
        val kuantitas1 = binding.etKuantitas1.text.toString()
        val barang2 = binding.etBarang2.text.toString()
        val kuantitas2 = binding.etKuantitas2.text.toString()

        val pdfDocument = PdfDocument()


        isiPDF(nama, telp, barang1, barang2, kuantitas1, kuantitas2, pdfDocument)


        file = File(this@MainActivity2.getExternalFilesDir(null), "dokumen.pdf")
        pdfDocument.writeTo(FileOutputStream(file))

        Log.d("PDF_PATH", file!!.absolutePath)

        // Tutup PdfDocument setelah selesai
        pdfDocument.close()

    }

    private fun isiPDF(nama: String, telp: String, barang1: String, barang2: String, kuantitas1: String, kuantitas2: String, pdfDocument: PdfDocument) {

        val drawableId = R.drawable.pizzahead
        val bitmap = BitmapFactory.decodeResource(resources, drawableId)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false)

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, 2010, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        val titlePaint = Paint()

        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint)

        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 70F
        canvas.drawText("Diamond Pizza", pageWidth/2f, 270f, titlePaint)

        paint.color = Color.rgb(0, 113, 180)
        paint.textSize = 30f


    }


}