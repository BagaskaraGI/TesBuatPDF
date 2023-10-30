package com.example.tesbuatpdf

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.tesbuatpdf.databinding.ActivityMainBinding
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.List
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Text
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Attributes.Name

class MainActivity : AppCompatActivity() {
    private var file: File? = null
    private lateinit var binding: ActivityMainBinding


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

        binding.btnSignPDF.setOnClickListener {
            signPDF()
        }

        binding.btnShowPDF.setOnClickListener {
            file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")
            val pdfFileName =
                file?.absolutePath // Gunakan objek File yang telah Anda buat sebelumnya

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



    private fun createPDF() {
//        val pdfDocument = PdfDocument()
//        buatPDF1(pdfDocument)
//        buatPDF2(pdfDocument, this)

//----------------------AMBIL FORMAT PDF-------------------------
        val pdfInputStream = resources.openRawResource(R.raw.format_skpi2)
        val temporaryFile = File.createTempFile("temporary_pdf", ".pdf")
        val temporaryOutputStream = FileOutputStream(temporaryFile)

        val buffer = ByteArray(1024)
        var length: Int
        while (pdfInputStream.read(buffer).also { length = it } > 0) {
            temporaryOutputStream.write(buffer, 0, length)
        }

        temporaryOutputStream.close()
        pdfInputStream.close()

        //----------------------Buka dan Edit PDF-------------------------


        // tempat output hasil pdf
        val pdfReader = PdfReader(temporaryFile.absolutePath)
        file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")

        val writer = PdfWriter(file)

//        val pdfDocument2 = com.itextpdf.kernel.pdf.PdfDocument(writer)
        val pdfDocument3 = com.itextpdf.kernel.pdf.PdfDocument(pdfReader , writer)

//        val document = Document(pdfDocument2)

        val document2 = Document(pdfDocument3)

        FileOutputStream(file)


        val page = pdfDocument3.getPage(1)
        val canvas = PdfCanvas(page)

//        buatPDF3(pdfDocument2, document, this)

        // Tentukan posisi dan ukuran teks
        val x = 280.0
        val y = 500.0
        val fontSize = 11f

        addTextToCanvas(canvas, x = 250.0, y = 682.0, fontSize = 11f, "Nomor SKPI")
        addTextToCanvas(canvas, x = 150.0, y = 597.0, fontSize = 11f, "Nomor Surat Keterangan Laporan Kepolisian")
        addTextToCanvas(canvas, x = x, y = 535.0, fontSize = 11f, "Nomor Ijazah/STTB")
        addTextToCanvas(canvas, x = x, y = 508.0, fontSize = 11f, "Nama Pemohon")
        addTextToCanvas(canvas, x = x, y = 480.0, fontSize = 11f, "Tempat Tanggal Lahir")
        addTextToCanvas(canvas, x = x, y = 453.0, fontSize = 11f, "Nama Orang Tua")
        addTextToCanvas(canvas, x = x, y = 425.0, fontSize = 11f, "NIS/NISN")
        addTextToCanvas(canvas, x = x, y = 397.0, fontSize = 11f, "Tahun Pelajaran")
        addTextToCanvas(canvas, x = 350.0, y = 350.0, fontSize = 11f, "Tahun Pelajaran2")
        addTextToCanvas(canvas, x = 350.0, y = 265.0, fontSize = 11f, "Tanggal Surat")
        addTextToCanvas(canvas, x = 310.0, y = 100.0, fontSize = 11f, "Nama Kadis")
        addTextToCanvas(canvas, x = 345.0, y = 80.0, fontSize = 11f, "NIP Kadis")

//        //Mau Bikin qr code
//        val qrCode = BarcodeQRCode("Hello World, This is Bagas")
//        val barcodeObj = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument3)
//        val barcodeImage = com.itextpdf.layout.element.Image(barcodeObj).setWidth(100f).setHeight(100f)
//
//        barcodeImage.setFixedPosition(350f,115f)
//
//        document2.add(barcodeImage)


        val drawableId = R.drawable.muhammad_aji_saputra
        val bitmap = BitmapFactory.decodeResource(resources, drawableId)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = com.itextpdf.layout.element.Image(imageData)
            .setHeight(150f)
            .setWidth(112.5f)

        image.setFixedPosition(90f, 125f)

        document2.add(image)

        // Tutup dokumen PDF
        document2.close()

        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show()

//        pdfDocument.writeTo(FileOutputStream(file))

        Log.d("PDF_PATH", file!!.absolutePath)

        // Tutup PdfDocument setelah selesai
//        pdfDocument.close()
    }



    private fun signPDF() {


        try {
            val file = File(this@MainActivity.getExternalFilesDir(null), "dokumen.pdf")
            val output = File(this@MainActivity.getExternalFilesDir(null), "hasilEdit.pdf")
            val reader = PdfReader(file)
            val writer = PdfWriter(output)
            // Lanjutkan dengan pengolahan PDF

            val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(reader, writer)
            val document = Document(pdfDocument)

            FileOutputStream(output)

            //Mau Bikin qr code
            val qrCode = BarcodeQRCode("Hello World, This is Bagas")
            val barcodeObj = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument)
            val barcodeImage = com.itextpdf.layout.element.Image(barcodeObj).setWidth(100f).setHeight(100f)

            barcodeImage.setFixedPosition(350f,115f)

            document.add(barcodeImage)

            document.close() // Simpan hasil perubahan ke file yang sama

            // Contoh jika Anda ingin menyalin file writer ke file reader:
            output.copyTo(file,true)

            // Hapus file sementara jika diperlukan
            output.delete()



            Toast.makeText(this, "Pdf Signed", Toast.LENGTH_LONG).show()
        } catch (e: com.itextpdf.io.IOException) {
            e.printStackTrace()
            // Tangani kesalahan, seperti memberikan pesan kesalahan kepada pengguna
        }catch (e: Exception) {
            // Tangani pengecualian di sini
            e.printStackTrace()
            Log.e("Exception", "Terjadi pengecualian: ${e.message}")
        } catch (e: Throwable) {
            // Tangani pengecualian di sini
            e.printStackTrace()
            Log.e("Throwable", "Terjadi pengecualian: ${e.message}")
        }
//        val reader = PdfReader(file)
//        val writer = PdfWriter(file)

    }

    fun addTextToCanvas(
        canvas: PdfCanvas,
        x: Double,
        y: Double,
        fontSize: Float,
        text: String
    ) {
        canvas.setFontAndSize(PdfFontFactory.createFont(), fontSize)
        canvas.beginText()
        canvas.moveText(x, y)
        canvas.showText(text)
        canvas.endText()
    }

}