package network.devandroid.com.networklibrarycomparison.internal

import android.content.Context

import network.devandroid.com.networklibrarycomparison.asyntask.AsyncNetworkManager
import network.devandroid.com.networklibrarycomparison.fastnetworking.FastNetworkManager
import network.devandroid.com.networklibrarycomparison.okhttp.OkHttpNetworkManager
import network.devandroid.com.networklibrarycomparison.retrofit.RetrofitNetworkManager
import network.devandroid.com.networklibrarycomparison.rxjava.RxJavaNetworkManager
import network.devandroid.com.networklibrarycomparison.volley.VolleyNetworkManager

object NetFactory {

    fun getNetworkManager(context: Context, type: NetType, callback: INetworkManager.Callback): BaseNetworkManager? {
        when (type) {
            NetType.VOLLEY -> return VolleyNetworkManager(callback, context)
            NetType.RETROFIT -> return RetrofitNetworkManager(callback)
            NetType.OK_HTTP -> return OkHttpNetworkManager(callback)
            NetType.FAST_NETWORK -> return FastNetworkManager(callback)
            NetType.ASYNC_TASK -> return AsyncNetworkManager(callback)
            NetType.RXJAVA -> return RxJavaNetworkManager(callback)
        }
        return null
    }
}
