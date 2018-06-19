package network.devandroid.com.networklibrarycomparison.rxjava

import android.os.Handler
import android.os.Looper
import android.util.Log

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager

class RxJavaNetworkManager(mCallback: INetworkManager.Callback<*>) : BaseNetworkManager<String>(mCallback) {

    override fun send(url: String) {
        getStringObservable(url)
                .subscribe(object : DisposableObserver<String>() {
                    override fun onNext(response: String) {
                        if (null != mCallback) {
                            Handler(Looper.getMainLooper()).post { mCallback.onResponse(response) }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (null != mCallback) {
                            Handler(Looper.getMainLooper()).post { mCallback.onError(e.localizedMessage) }
                        }
                    }

                    override fun onComplete() {

                    }
                })
    }

    @Throws(IOException::class)
    private fun getData(srcUrl: String): String {
        Log.d(TAG, "doInBackground: START")
        val urlConnection: HttpURLConnection
        var output: String? = null
        val url = URL(srcUrl)
        urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = BufferedInputStream(urlConnection.inputStream)
            output = readStream(`in`)
        } finally {
            urlConnection.disconnect()
        }

        Log.d(TAG, "doInBackground: END")
        return output
    }

    private fun readStream(`in`: InputStream): String {
        var reader: BufferedReader? = null
        val response = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(`in`))
            var line = ""
            while ((line = reader.readLine()) != null) {
                response.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return response.toString()
    }

    private fun getStringObservable(url: String): Observable<String> {
        return Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext(getData(url))
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {

        private val TAG = "RxJavaNetworkManager"
    }
}
