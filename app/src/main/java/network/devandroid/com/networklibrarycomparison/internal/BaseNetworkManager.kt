package network.devandroid.com.networklibrarycomparison.internal

abstract class BaseNetworkManager<T>(  protected  var mCallback: INetworkManager.Callback<*>) : INetworkManager<T>
