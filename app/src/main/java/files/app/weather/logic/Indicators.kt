package files.app.weather.logic

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var responseErrorIndicator by mutableStateOf(false)
var internetConnection by mutableStateOf(false)
val navigationError = mutableStateOf(false)

/*
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true
    }
    return false
}*/
