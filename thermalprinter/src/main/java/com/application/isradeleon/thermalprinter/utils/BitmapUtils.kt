package com.application.isradeleon.thermalprinter.utils

import android.graphics.Bitmap
import android.graphics.Color
import java.io.ByteArrayOutputStream

infix fun Byte.shl(that: Int): Int = this.toInt().shl(that)
infix fun Int.shl(that: Byte): Int = this.shl(that.toInt()) // Not necessary in this case because no there's (Int shl Byte)
infix fun Byte.shl(that: Byte): Int = this.toInt().shl(that.toInt()) // Not necessary in this case because no there's (Byte shl Byte)

infix fun Byte.and(that: Int): Int = this.toInt().and(that)
infix fun Int.and(that: Byte): Int = this.and(that.toInt()) // Not necessary in this case because no there's (Int and Byte)
infix fun Byte.and(that: Byte): Int = this.toInt().and(that.toInt())

class BitmapUtils {
    companion object{

        fun bitmapToByteArray(bitmap: Bitmap): ByteArray{
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            val byteArray = stream.toByteArray()
            bitmap.recycle()
            return byteArray
        }

        /*private val hexStr = "0123456789ABCDEF"
        private val binaryArray = arrayOf("0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111")

        fun bitmapToByteArray(bitmap: Bitmap): ByteArray{
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            val byteArray = stream.toByteArray()
            bitmap.recycle()
            return byteArray
        }

        fun decodeBitmap(bmp: Bitmap): ByteArray{
            val bmpWidth = bmp.getWidth();
            val bmpHeight = bmp.getHeight();

            val list = ArrayList<String>() //binaryString list
            var sb: StringBuffer

            var bitLen = bmpWidth / 8;
            val zeroCount = bmpWidth % 8

            val zeroStr = StringBuilder()
            if (zeroCount > 0) {
                bitLen = bmpWidth / 8 + 1;
                for (i in 0 until (8 - zeroCount)) {
                    zeroStr.append("0")
                }
            }

            for (i in 0 until bmpHeight) {
                sb = StringBuffer()
                for (j in 0 until bmpWidth) {
                    val color = bmp.getPixel(j, i)

                    val r = (color shr 16) and 0xff;
                    val g = (color shr 8) and 0xff;
                    val b = color and 0xff

                    // if color close to white，bit='0', else bit='1'
                    if (r > 160 && g > 160 && b > 160)
                        sb.append("0");
                    else
                        sb.append("1");
                }
                if (zeroCount > 0) {
                    sb.append(zeroStr);
                }
                list.add(sb.toString());
            }

            val bmpHexList = binaryListToHexStringList(list);
            val commandHexString = "1D763000";

            var widthHexString = Integer.toHexString(
                if (bmpWidth % 8 == 0) bmpWidth / 8
                else (bmpWidth / 8 + 1)
            )

            if (widthHexString.length > 2) {
                println("decodeBitmap error width is too large");
                return byteArrayOf()
            } else if (widthHexString.length == 1) {
                widthHexString = "0" + widthHexString;
            }
            widthHexString += "00";

            var heightHexString = Integer.toHexString(bmpHeight);
            if (heightHexString.length > 2) {
                println("decodeBitmap error height is too large");
                return byteArrayOf()
            } else if (heightHexString.length == 1) {
                heightHexString = "0" + heightHexString;
            }
            heightHexString += "00";

            val commandList = ArrayList<String>()
            commandList.add(commandHexString+widthHexString+heightHexString);
            commandList.addAll(bmpHexList);

            return hexList2Byte(commandList);
        }

        private fun hexList2Byte(list: ArrayList<String>): ByteArray {
            val commandList = ArrayList<ByteArray>();
            for (hexStr in list) {
                commandList.add(hexStringToBytes(hexStr));
            }
            val bytes = sysCopy(commandList);
            return bytes;
        }

        private fun hexStringToBytes(_hexString: String): ByteArray {
            if (_hexString.isEmpty()) {
                return byteArrayOf()
            }
            val hexString = _hexString.toUpperCase(Locale.getDefault());
            val length = hexString.length / 2;

            val hexChars = hexString.toCharArray()
            val d = ByteArray(length)

            for (i in 0 until length) {
                val pos = i * 2
                d[i] = (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }
            return d;
        }

        private fun charToByte(c: Char): Byte {
            return "0123456789ABCDEF".indexOf(c).toByte()
        }

        private fun binaryListToHexStringList(list: ArrayList<String>): Collection<String> {
            val hexList = ArrayList<String>()
            for (binaryStr in list) {
                val sb = StringBuffer()
                for (i in 0 until binaryStr.length) {
                    val str = binaryStr.substring(i, i + 8)

                    val hexString = myBinaryStrToHexString(str)
                    sb.append(hexString)
                }
                hexList.add(sb.toString());
            }
            return hexList;
        }

        private fun myBinaryStrToHexString(binaryStr: String): Any {
            var hex = "";
            val f4 = binaryStr.substring(0, 4);
            val b4 = binaryStr.substring(4, 8);
            for (i in 0 until binaryArray.size) {
                if (f4.equals(binaryArray[i]))
                    hex += hexStr.substring(i, i + 1);
            }
            for (i in 0 until binaryArray.size) {
                if (b4.equals(binaryArray[i]))
                    hex += hexStr.substring(i, i + 1);
            }

            return hex;
        }

        */

        fun bitmapToBlackAndWhite(src: Bitmap): Bitmap{
            val width = src.getWidth()
            val height = src.getHeight()

            // create output bitmap
            val bmOut = Bitmap.createBitmap(width, height, src.getConfig())

            // get contrast value
            val contrast = Math.pow((100 + 1.0) / 100, 2.0)

            // scan through all pixels
            for(x in 0 until width) {
                for(y in 0 until height) {
                    // get pixel color
                    val pixel = src.getPixel(x, y)
                    val A = Color.alpha(pixel)
                    // apply filter contrast for every channel R, G, B
                    var R = Color.red(pixel)
                    R = (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()

                    if(R < 0) { R = 0 }
                    else if(R > 255) { R = 255 }

                    var G = Color.red(pixel)
                    G = (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()

                    if(G < 0) { G = 0 }
                    else if(G > 255) { G = 255 }

                    var B = Color.red(pixel)
                    B = (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()
                    if(B < 0) { B = 0 }
                    else if(B > 255) { B = 255 }

                    // set new pixel color to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B))
                }
            }

            return bmOut;
        }
    }

}