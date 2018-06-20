package network.devandroid.com.networklibrarycomparison.volley

import android.content.Context
import android.os.Handler
import android.os.Looper

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager

class VolleyNetworkManager(callback: INetworkManager.Callback, private val context: Context) : BaseNetworkManager(callback) {

    override fun send(url: String) {
        val stringRequest = StringRequest(url, Response.Listener { response ->
            if (null != callback) {
                Handler(Looper.getMainLooper()).post { callback.onResponse(response) }
            }
        }, Response.ErrorListener { error ->
            if (null != callback) {
                Handler(Looper.getMainLooper()).post { callback.onError(error.localizedMessage) }
            }
        })
        //disable the cache and add request
        stringRequest.setShouldCache(false)
        val volleyNetwork = VolleyNetwork.getInstance(context)
        volleyNetwork?.requestQueue?.cache?.clear()
        volleyNetwork?.addToRequestQueue(stringRequest)
    }
}
