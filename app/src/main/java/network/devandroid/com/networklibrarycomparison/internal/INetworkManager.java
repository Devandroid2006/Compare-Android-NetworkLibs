package network.devandroid.com.networklibrarycomparison.internal;

public interface INetworkManager<T> {

    void send(String url);

    interface Callback<T> {
        void onResponse(T response);

        void onError(T error);
    }
}
