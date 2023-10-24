package com.example.tesbuatpdf

import android.content.Intent
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity2 : AppCompatActivity() {

    private var file: File? = null
    private lateinit var binding: ActivityMain2Binding
    val pageWidth = 1200
    private lateinit var calendar : Calendar




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        calendar = Calendar.getInstance()

        setContentView(binding.root)

        binding.btnCreatePDF2.setOnClickListener {
            createPDF()
        }

        binding.btnOpenPDF2.setOnClickListener {
            val intent = Intent(this, PDFViewActivity::class.java)
            startActivity(intent)
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
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Call: 0888-89898989", 1160f, 40f, paint)
        canvas.drawText("022-134575634", 1160f, 80f, paint)


        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        titlePaint.textSize = 70F
        canvas.drawText("Invoice", pageWidth/2f, 500f, titlePaint)

        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 35f
        paint.color = Color.BLACK
        canvas.drawText("Customer Name: $nama", 20f, 590f, paint)
        canvas.drawText("Contact No.: $telp", 20f, 640f, paint)

        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Invoice No. :" + "23232323", pageWidth-20f, 590f, paint)


        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        canvas.drawText("Date : $currentDate", pageWidth-20f, 640f, paint)

        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = timeFormat.format(calendar.time)
        canvas.drawText("Time : $currentTime", pageWidth-20f, 690f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.drawRect(20f, 780f, pageWidth-20f, 840f, paint)

        paint.textAlign = Paint.Align.LEFT
        paint.style = Paint.Style.FILL
        canvas.drawText("B1. No.", 40f, 830f, paint)
        canvas.drawText("Item Description.", 200f, 830f, paint)
        canvas.drawText("Price.", 650f, 830f, paint)
        canvas.drawText("Qty", 900f, 830f, paint)
        canvas.drawText("Total", 1060f, 830f, paint)

        canvas.drawLine(180f, 790f, 180f, 840f, paint)
        canvas.drawLine(600f, 790f, 600f, 840f, paint)
        canvas.drawLine(800f, 790f, 800f, 840f, paint)
        canvas.drawLine(1030f, 790f, 1030f, 840f, paint)


        // Selesai dengan halaman
        pdfDocument.finishPage(page)

    }


}