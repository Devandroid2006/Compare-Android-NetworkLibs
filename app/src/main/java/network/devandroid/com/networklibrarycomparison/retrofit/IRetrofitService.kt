package network.devandroid.com.networklibrarycomparison.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface IRetrofitService {

    @get:GET("/photos")
    val photoList: Call<ResponseBody>
}
