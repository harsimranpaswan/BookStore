package com.workshop.practice.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import java.net.NetworkInterface

class ConnectionManager {
    @SuppressLint("SuspiciousIndentation")
    fun checkConnectivity(context: Context): Boolean{
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? =connectivityManager.activeNetworkInfo
            if (activeNetwork?.isConnected!=null)
                return activeNetwork.isConnected
            else
                return false

    }
}