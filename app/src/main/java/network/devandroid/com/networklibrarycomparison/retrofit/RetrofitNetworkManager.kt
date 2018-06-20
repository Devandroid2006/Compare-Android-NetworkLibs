package network.devandroid.com.networklibrarycomparison.retrofit

import android.os.Handler
import android.os.Looper

import java.io.IOException

import network.devandroid.com.networklibrarycomparison.ICommonConstants
import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkManager(callback: INetworkManager.Callback) : BaseNetworkManager(callback) {

    private//disable cache
    val retrofit: Retrofit
        get() {
            val client = OkHttpClient.Builder()
                    .cache(null)
                    .build()
            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(ICommonConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

    private val retrofitService: IRetrofitService
        get() = retrofit.create(IRetrofitService::class.java!!)

    override fun send(url: String) {
        retrofitService.photoList.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (null != callback) {
                    Handler(Looper.getMainLooper()).post {
                        try {
                            callback.onResponse(response.body()!!.string().toString())
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                if (null != callback) {
                    Handler(Looper.getMainLooper()).post { callback.onError(throwable.localizedMessage) }
                }
            }
        })
    }
}
