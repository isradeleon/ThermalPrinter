package com.application.isradeleon.thermalprinterexample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.isradeleon.thermalprinter.ConnectBluetoothActivity
import com.application.isradeleon.thermalprinter.models.PrintAlignment
import com.application.isradeleon.thermalprinter.models.PrintFont
import com.application.isradeleon.thermalprinter.models.ThermalPrinter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivityForResult(
            Intent(this, ConnectBluetoothActivity::class.java),
            ConnectBluetoothActivity.CONNECT_BLUETOOTH
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == ConnectBluetoothActivity.CONNECT_BLUETOOTH){
            ThermalPrinter.instance
                .write("Hello world", PrintAlignment.CENTER, PrintFont.LARGE)
                .fillLineWith('-')
                .write("Let's eat","some tacos")
                .write("Price","$1 USD")
                .print()
        }
    }
}
