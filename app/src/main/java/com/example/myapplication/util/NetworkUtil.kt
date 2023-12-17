import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkUtil : ConnectivityManager.NetworkCallback() {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkMonitor(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerDefaultNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        _isConnected.postValue(true)
    }

    override fun onLost(network: Network) {
        _isConnected.postValue(false)
    }
}
