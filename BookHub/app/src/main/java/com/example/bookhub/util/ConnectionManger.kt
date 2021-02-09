package com.example.bookhub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

class ConnectionManger  {
    fun checkConncetivity (context: Context) : Boolean
    {
        val connectivityManger = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork : NetworkInfo ? = connectivityManger.activeNetworkInfo

        if(activeNetwork?.isConnected!=null)
        {
            return activeNetwork.isConnected
        }
        else
        {
            return false
        }

    }
}