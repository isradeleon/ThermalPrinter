package com.application.isradeleon.thermalprinter.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.application.isradeleon.thermalprinter.printer.PrinterCommands
import com.application.isradeleon.thermalprinter.utils.BitmapHelper
import com.application.isradeleon.thermalprinter.utils.BitmapUtils
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.NullPointerException

class ThermalPrinter {

    private val charsPerRow = 40

    companion object{
        val instance = ThermalPrinter()
    }

    fun fillLineWith(char: Char): ThermalPrinter{
        write(char.toString().repeat(charsPerRow), PrintAlignment.CENTER)
        return this
    }

    fun writeImage(bitmap: Bitmap): ThermalPrinter{
        try {
            callPrinter(alignmentToCommand(PrintAlignment.CENTER))
            callPrinter(BitmapHelper.decodeBitmap(bitmap))
            printAndLine()
        }catch (e: IllegalStateException){
            write("*Error: image might be too large or not black & white format*")
        }
        return this
    }

    fun write(key: String, value: String, separator: Char = '.'): ThermalPrinter{
        val ans = key + value;
        if(ans.length <= charsPerRow){
            write((key+separator.toString().repeat(charsPerRow-ans.length)+value), PrintAlignment.CENTER)
        }else{
            write(key+" : "+value);
        }
        return this
    }

    fun write(
        text: String,
        alignment: PrintAlignment = PrintAlignment.LEFT,
        font: PrintFont = PrintFont.NORMAL
    ): ThermalPrinter{
        callPrinter(alignmentToCommand(alignment))
        callPrinter(fontToCommand(font))
        callPrinter(text.toByteArray())
        printAndLine()
        return this
    }

    fun newLine(): ThermalPrinter{
        callPrinter(PrinterCommands.FEED_LINE)
        return this
    }

    private fun alignmentToCommand(alignment: PrintAlignment): ByteArray{
        when(alignment){
            PrintAlignment.CENTER -> return PrinterCommands.ESC_ALIGN_CENTER
            PrintAlignment.LEFT-> return PrinterCommands.ESC_ALIGN_LEFT
            PrintAlignment.RIGHT-> return PrinterCommands.ESC_ALIGN_RIGHT
        }
    }

    private fun fontToCommand(font: PrintFont): ByteArray{
        when(font){
            PrintFont.NORMAL -> return PrinterCommands.FONT_NORMAL
            PrintFont.LARGE -> return PrinterCommands.FONT_MEDIUM
        }
    }

    private fun callPrinter(bytes: ByteArray){
        callSecure {
            BluetoothConnection.bluetoothSocket.outputStream.write(bytes,0,bytes.size)
        }
    }

    private fun printAndLine(){
        callSecure {
            BluetoothConnection.bluetoothSocket.outputStream.write(PrinterCommands.LF)
        }
    }

    fun print(){
        newLine()
        newLine()
        callSecure {
            BluetoothConnection.bluetoothSocket.outputStream.flush()
        }
    }

    private fun callSecure(call: ()->Unit){
        try {
            call()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}