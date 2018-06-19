package network.devandroid.com.networklibrarycomparison.fastnetworking

import android.os.Handler
import android.os.Looper

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager

class FastNetworkManager(callback: INetworkManager.Callback<*>) : BaseNetworkManager<String>(callback) {

    override fun send(url: String) {
        AndroidNetworking.get(url)
                .setTag("test")
                .doNotCacheResponse()
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        if (null != mCallback) {
                            Handler(Looper.getMainLooper()).post { mCallback.onResponse(response) }
                        }
                    }

                    override fun onError(anError: ANError) {
                        if (null != mCallback) {
                            Handler(Looper.getMainLooper()).post { mCallback.onError(anError.localizedMessage) }
                        }
                    }
                })
    }
}
