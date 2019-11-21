package com.application.isradeleon.thermalprinter.adapters

import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.isradeleon.thermalprinter.R
import com.application.isradeleon.thermalprinter.models.BluetoothConnection
import com.application.isradeleon.thermalprinter.utils.toast
import kotlinx.android.synthetic.main.item_bluetooth_device.view.*

class DevicesAdapter(val context: Context, val devices: List<BluetoothDevice>): RecyclerView.Adapter<DevicesAdapter.BDeviceHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BDeviceHolder {
        val inflater = LayoutInflater.from(context)
        return BDeviceHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: BDeviceHolder, position: Int) {
        holder.bind(devices.get(position))
    }

    class BDeviceHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_bluetooth_device, parent, false)){

        fun bind(device: BluetoothDevice) {
            itemView.name.setText(
                if (device.name == null || device.name.equals("null") || device.name.isEmpty())
                    "-"
                else
                    device.name
            )

            itemView.address.setText(device.address)

            itemView.setOnClickListener{
                it.toast("Connecting...")
                BluetoothConnection.connectDevice(device, object: BluetoothConnection.ConnectionListener{
                    override fun onConnected() {
                        it.toast("Connected")
                        (it.context as AppCompatActivity).setResult(RESULT_OK)
                        (it.context as AppCompatActivity).finish()
                    }

                    override fun onConnectionError() {
                        it.toast("Connection error")
                    }
                })
            }
        }
    }
}