package com.angelodev.helpapp.util

import android.content.Context
import android.os.Environment
import com.angelodev.helpapp.data.local.entity.CircuitEntity
import com.angelodev.helpapp.data.model.LoadScheduleResult
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PdfGenerator(private val context: Context) {

    fun generatePdf(
        circuits: List<CircuitEntity>,
        result: LoadScheduleResult,
        projectName: String
    ): File? {
        return try {
            val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val timestamp = dateFormat.format(Date())
            val fileName = "LoadSchedule_${projectName}_$timestamp.pdf"

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) downloadsDir.mkdirs()
            val file = File(downloadsDir, fileName)

            val document = Document(PageSize.LEGAL.rotate(), 20f, 20f, 20f, 20f)
            PdfWriter.getInstance(document, FileOutputStream(file))
            document.open()

            // Title
            val titleFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
            val headerFont = Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD)
            val cellFont = Font(Font.FontFamily.HELVETICA, 7f, Font.NORMAL)

            val title = Paragraph("LOAD SCHEDULE - $projectName", titleFont)
            title.alignment = Element.ALIGN_CENTER
            document.add(title)
            document.add(Paragraph("\n"))

            // Summary info
            val summaryFont = Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL)
            document.add(Paragraph("Load Name: ${result.loadName}", summaryFont))
            document.add(Paragraph("Total VA: ${ElectricalCalculator.formatValue(result.totalVA)}", summaryFont))
            document.add(Paragraph("Total Ampere: ${ElectricalCalculator.formatValue(result.totalAmpere)}", summaryFont))
            document.add(Paragraph("Highest Ampere: ${ElectricalCalculator.formatValue(result.highestAmpere)}", summaryFont))
            document.add(Paragraph("Demand Factor: ${result.demandFactor}", summaryFont))
            document.add(Paragraph("\n"))

            // Table
            val table = PdfPTable(18)
            table.widthPercentage = 100f

            // Headers
            val headers = arrayOf(
                "No.", "Qty", "Item", "O+", "V", "VA", "A", "P",
                "AT", "AF", "S#", "Smm", "SType", "G#", "Gmm", "GType", "mm+", "CType"
            )
            headers.forEach { header ->
                val cell = PdfPCell(Phrase(header, headerFont))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                cell.backgroundColor = BaseColor(21, 101, 192) // primary color
                cell.setPadding(3f)
                val fontWhite = Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD, BaseColor.WHITE)
                cell.phrase = Phrase(header, fontWhite)
                table.addCell(cell)
            }

            // Data rows
            circuits.forEachIndexed { index, circuit ->
                val values = arrayOf(
                    "${index + 1}", circuit.quantity, circuit.item, circuit.oPlus,
                    circuit.voltage, circuit.voltAmpere, circuit.ampere, circuit.pole,
                    circuit.ampereTrip, circuit.ampereFrame, circuit.sizeNum, circuit.sizeMm,
                    circuit.sizeType, circuit.groundNum, circuit.groundMm, circuit.groundType,
                    circuit.mmPlus, circuit.conduitType
                )
                values.forEach { value ->
                    val cell = PdfPCell(Phrase(value, cellFont))
                    cell.horizontalAlignment = Element.ALIGN_CENTER
                    cell.setPadding(2f)
                    if (index % 2 == 1) {
                        cell.backgroundColor = BaseColor(240, 240, 240)
                    }
                    table.addCell(cell)
                }
            }

            document.add(table)
            document.close()

            Timber.d("PDF generated: ${file.absolutePath}")
            file
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate PDF")
            null
        }
    }
}
