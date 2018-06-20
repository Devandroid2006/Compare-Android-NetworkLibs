package network.devandroid.com.networklibrarycomparison

import android.app.Application

import com.androidnetworking.AndroidNetworking


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

}
