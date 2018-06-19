package network.devandroid.com.networklibrarycomparison.asyntask

import android.os.Handler
import android.os.Looper

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager

class AsyncNetworkManager(callback: INetworkManager.Callback<*>) : BaseNetworkManager<String>(callback) {

    override fun send(url: String) {
        object : FetchAsyncTask(url) {
            override fun onPostExecute(result: String?) {
                if (null != mCallback) {
                    if (null != result) {
                        Handler(Looper.getMainLooper()).post { mCallback.onResponse(result) }
                    } else {
                        Handler(Looper.getMainLooper()).post { mCallback.onError("Error loading...") }
                    }
                }

            }
        }.execute()
    }
}
