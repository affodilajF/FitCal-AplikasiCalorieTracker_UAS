package com.example.myapplication.view.menuUser.home

import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel : ViewModel() {

    fun getTodayDate() : String {
        val calendar = Calendar.getInstance()
        val tahun = calendar.get(Calendar.YEAR)
        val bulan = calendar.get(Calendar.MONTH) + 1
        val tanggal = calendar.get(Calendar.DAY_OF_MONTH)

        val hariSekarang = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        var tanggalSekarang = "$hariSekarang, $tahun-$bulan-$tanggal"

        return tanggalSekarang
    }


}