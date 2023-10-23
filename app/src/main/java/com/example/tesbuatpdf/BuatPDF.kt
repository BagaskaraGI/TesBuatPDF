package com.example.tesbuatpdf

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.media.Image

fun buatPDF1(pdfDocument: PdfDocument){
    var informationArray = listOf<String>("Name", "Company Name", "Address", "Phone", "Email")


    val pageInfo = PdfDocument.PageInfo.Builder(250, 400, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    // Atur ukuran font dan warna teks
    paint.textSize = 12f
    paint.color = Color.BLACK
    paint.textAlign = Paint.Align.CENTER

    canvas.drawText("TES BUAT PDF BAGAS", pageInfo.pageWidth.toFloat()/2, 30f, paint)

    paint.textSize = 6f
    paint.color = Color.rgb(122,119,119)
    canvas.drawText("Cevest Bbplk Bekasi, BBPLK, Jl. Guntur Raya No.1, Kayuringin Jaya", pageInfo.pageWidth.toFloat()/2, 40f, paint)

    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 9f
    paint.color = Color.rgb(122,119,119)
    canvas.drawText("Customer Information", 10f, 70f, paint)

    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 8f
    paint.color = Color.BLACK

    var startXPosition = 10f
    val endXPosition = pageInfo.pageWidth - 10f
    var startYPosition = 100f


    informationArray.forEach{
        canvas.drawText(it, startXPosition, startYPosition, paint)
        canvas.drawLine(startXPosition, startYPosition+3, endXPosition, startYPosition+3, paint)
        startYPosition += 20
    }

    canvas.drawLine(80f, 92f, 80f, 190f, paint)

    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 2f
    canvas.drawRect(10f, 200f, endXPosition, 300f, paint)
    canvas.drawLine(85f, 200f, 85f, 300f, paint)
    canvas.drawLine(163f, 200f, 163f, 300f, paint)

    paint.strokeWidth = 0f
    paint.style = Paint.Style.FILL

    for(x in 30 until 270 step 80){
        canvas.drawText("Photo", x.toFloat(), 250f, paint)
    }

    canvas.drawText("Note:", 10f, 320f, paint)
    canvas.drawLine(35f, 325f, endXPosition, 325f, paint)
    canvas.drawLine(10f, 345f, endXPosition, 345f, paint)
    canvas.drawLine(10f, 365f, endXPosition, 365f, paint)


    // Selesai dengan halaman
    pdfDocument.finishPage(page)
}


fun buatPDF2(pdfDocument: PdfDocument, context: Context){
    val pageInfo = PdfDocument.PageInfo.Builder(250, 400, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val drawableId = R.drawable.disdik_bekasi
    val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false)

    val canvas = page.canvas
    val paint = Paint()

    canvas.drawBitmap(scaledBitmap, pageInfo.pageWidth/4f, pageInfo.pageHeight/4f, paint)

    // Selesai dengan halaman
    pdfDocument.finishPage(page)


}