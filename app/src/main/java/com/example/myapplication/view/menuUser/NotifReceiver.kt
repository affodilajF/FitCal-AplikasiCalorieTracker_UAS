package com.example.myapplication.view.menuUser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class NotifReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val msg = intent?.getStringExtra("MSG")
        if(msg !=null){
            Log.d("Notif Receiver", "Baca notif clicked")
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}