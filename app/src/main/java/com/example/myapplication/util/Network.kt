package com.example.myapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

object Network {

    private lateinit var connectivityLiveData: MutableLiveData<Boolean>
    private lateinit var coroutineScope: CoroutineScope

    fun startCheckingInternetConnectivity(context: Context): MutableLiveData<Boolean> {
        connectivityLiveData = MutableLiveData()
        coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            while (true) {
                val isConnected = isInternetAvailable(context)
                connectivityLiveData.postValue(isConnected)
                delay(5000) // Cek kondisi internet setiap 5 detik
            }
        }

        return connectivityLiveData
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }

    fun stopInternetConnectivityCheck() {
        coroutineScope.cancel()
    }
}
