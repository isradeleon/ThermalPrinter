package com.application.isradeleon.thermalprinter.printer

internal class PrinterCommands {
    companion object{
        val FEED_LINE = byteArrayOf(10)
        val ESC_ALIGN_LEFT = byteArrayOf(0x1b, 'a'.toByte(), 0x00)
        val ESC_ALIGN_CENTER = byteArrayOf(0x1b, 'a'.toByte(), 0x01)
        val ESC_ALIGN_RIGHT = byteArrayOf(0x1b, 'a'.toByte(), 0x02)
        val LF = 0x0A

        val FONT_NORMAL = byteArrayOf(0x1B,0x21,0x03)
        val FONT_MEDIUM = byteArrayOf(0x1B,0x21,0x20)
        val FONT_LARGE= byteArrayOf(0x1B,0x21,0x10)
        val FONT_BOLD = byteArrayOf(0x1B,0x21,0x08)
    }
}