package network.devandroid.com.networklibrarycomparison.asyntask;

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;

public class AsyncNetworkManager extends BaseNetworkManager<String> {

    public AsyncNetworkManager(Callback callback) {
        super(callback);
    }

    @Override
    public void send(String url) {
        new FetchAsyncTask(url) {
            @Override
            protected void onPostExecute(String result) {
                if (null != result) {
                    mCallback.onResponse(result);
                } else {
                    mCallback.onError("Error.");
                }
            }
        }.execute();
    }
}
