package network.devandroid.com.networklibrarycomparison.retrofit;

import android.os.Handler;
import android.os.Looper;

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
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (null != mCallback) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mCallback.onResponse(response.body().string().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable throwable) {
                if (null != mCallback) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onError(throwable.getLocalizedMessage());
                        }
                    });
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
