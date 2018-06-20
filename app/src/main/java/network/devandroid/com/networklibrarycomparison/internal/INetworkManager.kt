package network.devandroid.com.networklibrarycomparison.internal

interface INetworkManager {

    fun send(url: String)

    interface Callback {
        fun onResponse(response: String)

        fun onError(error: String)
    }
}
