package network.devandroid.com.networklibrarycomparison.internal

interface INetworkManager<T> {

    fun send(url: String)

    interface Callback<T> {
        fun onResponse(response: T)

        fun onError(error: T)
    }
}
