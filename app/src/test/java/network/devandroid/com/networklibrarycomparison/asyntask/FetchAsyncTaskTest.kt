package network.devandroid.com.networklibrarycomparison.asyntask

import org.junit.Test

import java.util.concurrent.CountDownLatch

import network.devandroid.com.networklibrarycomparison.ICommonConstants

class FetchAsyncTaskTest {

    @Test
    fun doInBackground() {

        val countDownLatch = CountDownLatch(1)

        val fetchAsyncTask = object : FetchAsyncTask(ICommonConstants.URL) {
            override fun onPostExecute(s: String) {
                super.onPostExecute(s)
                countDownLatch.countDown()
            }
        }

    }

    @Test
    fun readStream() {

    }
}