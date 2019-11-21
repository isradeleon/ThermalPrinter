package com.application.isradeleon.thermalprinter

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.isradeleon.thermalprinter.adapters.DevicesAdapter
import kotlinx.android.synthetic.main.activity_connect_printer.*
import java.lang.IllegalArgumentException

class ConnectBluetoothActivity : AppCompatActivity() {
    val BLUETOOTH_PERMISSION = 76
    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN)

    companion object{
        val CONNECT_BLUETOOTH = 606
    }

    val devices = mutableListOf<BluetoothDevice>()
    lateinit var deviceAdapter: DevicesAdapter
    lateinit var bluetoothAdapter: BluetoothAdapter

    val broadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent!!.action!!.equals(BluetoothDevice.ACTION_FOUND)){
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)!!
                if(!devices.contains(device)){
                    devices.add(device)
                    deviceAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_printer)
        setTitle("Bluetooth")

        deviceAdapter = DevicesAdapter(this, devices)

        recyclerDevices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = deviceAdapter
        }

        if (hasAllPermissions()) {
            findDevices()
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, BLUETOOTH_PERMISSION)
            }
        }
    }

    private fun hasAllPermissions(): Boolean{
        for (permission in PERMISSIONS)
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) return false
        return true
    }

    @SuppressLint("MissingPermission")
    private fun findDevices() {
        if(!BluetoothAdapter.getDefaultAdapter().isEnabled)
            BluetoothAdapter.getDefaultAdapter().enable()

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED)

        registerReceiver(broadcastReceiver, filter)
        bluetoothAdapter.startDiscovery()
    }

    override fun onDestroy() {
        try{
            unregisterReceiver(broadcastReceiver)
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == BLUETOOTH_PERMISSION){
            if(hasAllPermissions()){
                findDevices()
            }else{
                finish()
            }
        }
    }
}
