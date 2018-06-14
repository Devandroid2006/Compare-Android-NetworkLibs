package network.devandroid.com.networklibrarycomparison.asyntask;

import android.os.Handler;
import android.os.Looper;

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;

public class AsyncNetworkManager extends BaseNetworkManager<String> {

    public AsyncNetworkManager(Callback callback) {
        super(callback);
    }

    @Override
    public void send(String url) {
        new FetchAsyncTask(url) {
            @Override
            protected void onPostExecute(final String result) {
                if (null != mCallback) {
                    if (null != result) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onResponse(result);
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onError("Error loading...");
                            }
                        });
                    }
                }

            }
        }.execute();
    }
}
