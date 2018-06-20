package network.devandroid.com.networklibrarycomparison.asyntask

import android.os.AsyncTask
import android.util.Log

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * documentation
 */
open class FetchAsyncTask(private val mUrl: String) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg voids: Void): String? {
        Log.d(TAG, "doInBackground: START")
        val urlConnection: HttpURLConnection
        var output: String? = null
        try {
            val url = URL(mUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            try {
                val `in` = BufferedInputStream(urlConnection.inputStream)
                output = readStream(`in`)
            } finally {
                urlConnection.disconnect()
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.d(TAG, "doInBackground: END")
        return output
    }

    fun readStream(`in`: InputStream): String {
        var reader: BufferedReader? = null
        val response = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(`in`))
            var line:String? = reader.readLine()
            while (line  != null) {
                response.append(line)
                line = reader.readLine()
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

    companion object {

        private val TAG = "AsyncTaskNetwork"
    }


}
