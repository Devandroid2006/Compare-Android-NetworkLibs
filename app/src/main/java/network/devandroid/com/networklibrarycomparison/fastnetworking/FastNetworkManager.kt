package network.devandroid.com.networklibrarycomparison.fastnetworking

import android.os.Handler
import android.os.Looper

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager

class FastNetworkManager(callback: INetworkManager.Callback) : BaseNetworkManager(callback) {

    override fun send(url: String) {
        AndroidNetworking.get(url)
                .setTag("test")
                .doNotCacheResponse()
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        if (null != callback) {
                            Handler(Looper.getMainLooper()).post { callback.onResponse(response) }
                        }
                    }

                    override fun onError(anError: ANError) {
                        if (null != callback) {
                            Handler(Looper.getMainLooper()).post { callback.onError(anError.localizedMessage) }
                        }
                    }
                })
    }
}
