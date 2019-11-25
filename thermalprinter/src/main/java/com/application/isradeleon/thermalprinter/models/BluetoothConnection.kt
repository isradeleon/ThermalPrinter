package com.application.isradeleon.thermalprinter.models

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper

class BluetoothConnection {
    companion object{
        lateinit var bluetoothSocket: BluetoothSocket

        fun connectDevice(device: BluetoothDevice, connectionListener: ConnectionListener){
            AsyncTask.execute{
                try{
                    device.fetchUuidsWithSdp()
                    val uuid = device.uuids[0].uuid
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                    bluetoothSocket.connect()

                    Handler(Looper.getMainLooper()).post {
                        connectionListener.onConnected()
                    }

                }catch (e: Exception){
                    e.printStackTrace()

                    Handler(Looper.getMainLooper()).post {
                        connectionListener.onConnectionError()
                    }
                }
            }
        }
    }

    interface ConnectionListener{
        fun onConnected()
        fun onConnectionError()
    }
}