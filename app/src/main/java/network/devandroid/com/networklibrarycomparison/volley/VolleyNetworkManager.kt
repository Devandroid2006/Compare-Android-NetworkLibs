package network.devandroid.com.networklibrarycomparison.volley

import android.content.Context
import android.os.Handler
import android.os.Looper

import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager

class VolleyNetworkManager(callback: INetworkManager.Callback<*>, private val context: Context) : BaseNetworkManager<String>(callback) {

    override fun send(url: String) {
        val stringRequest = StringRequest(url, Response.Listener { response ->
            if (null != mCallback) {
                Handler(Looper.getMainLooper()).post { mCallback.onResponse(response) }
            }
        }, Response.ErrorListener { error ->
            if (null != mCallback) {
                Handler(Looper.getMainLooper()).post { mCallback.onError(error.localizedMessage) }
            }
        })
        //disable the cache and add request
        stringRequest.setShouldCache(false)
        val volleyNetwork = VolleyNetwork.getInstance(context)
        volleyNetwork.requestQueue.cache.clear()
        volleyNetwork.addToRequestQueue(stringRequest)
    }
}
