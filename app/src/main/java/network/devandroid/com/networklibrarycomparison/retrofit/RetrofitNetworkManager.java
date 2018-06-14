package network.devandroid.com.networklibrarycomparison.retrofit;

import java.io.IOException;

import network.devandroid.com.networklibrarycomparison.ICommonConstants;
import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkManager extends BaseNetworkManager<String> {

    public RetrofitNetworkManager(Callback callback) {
        super(callback);
    }

    @Override
    public void send(String url) {
        getRetrofitService().getPhotoList().enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != mCallback) {
                    try {
                        mCallback.onResponse(response.body().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (null != mCallback) {
                    mCallback.onError(t.getLocalizedMessage());
                }
            }
        });
    }

    private Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(null)//disable cache
                .build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ICommonConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private IRetrofitService getRetrofitService() {
        return getRetrofit().create(IRetrofitService.class);
    }
}
