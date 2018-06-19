package network.devandroid.com.networklibrarycomparison.okhttp

import android.os.Handler
import android.os.Looper

import java.io.IOException

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class OkHttpNetworkManager(mCallback: INetworkManager.Callback<*>) : BaseNetworkManager<String>(mCallback) {

    override fun send(url: String) {
        val client = OkHttpClient.Builder()
                .cache(null)//disable cache
                .build()
        val request = Request.Builder()
                .url(url)
                .cacheControl(CacheControl.Builder().noCache().build())
                .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (null != mCallback) {
                    Handler(Looper.getMainLooper()).post { mCallback.onError(e.localizedMessage) }
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (null != mCallback) {
                    val result = response.body()!!.string().toString()
                    Handler(Looper.getMainLooper()).post { mCallback.onResponse(result) }
                }
            }
        })
    }


}
