package network.devandroid.com.networklibrarycomparison.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitService {

    @GET("/photos")
    Call<ResponseBody> getPhotoList();
}
