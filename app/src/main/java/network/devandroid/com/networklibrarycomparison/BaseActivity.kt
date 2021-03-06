package network.devandroid.com.networklibrarycomparison

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import net.hockeyapp.android.CrashManager
import net.hockeyapp.android.UpdateManager

import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseActivity : AppCompatActivity() {

    private var unbinder: Unbinder? = null

    @get:LayoutRes
    protected abstract val layoutResource: Int

    protected abstract val getToolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        unbinder = ButterKnife.bind(this)

        //setup toolbar
        if (getToolBar != null) {
            setSupportActionBar(getToolBar)
            getToolBar.setNavigationIcon(R.drawable.ic_nav_back)
            getToolBar.setNavigationOnClickListener {
                //default call backpress
                onBackPressed()
            }
        }

        //force orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //check for hockey app updates
        checkForUpdates()
    }

    override fun onResume() {
        super.onResume()
        // ... your own onResume implementation
        checkForCrashes()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
        unregisterManagers()
    }

    private fun checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this)
    }

    private fun unregisterManagers() {
        UpdateManager.unregister()
    }

    private fun checkForCrashes() {
        CrashManager.register(this)
    }
}
