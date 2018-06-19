package network.devandroid.com.networklibrarycomparison

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import net.hockeyapp.android.CrashManager
import net.hockeyapp.android.UpdateManager

import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseActivity : AppCompatActivity() {

    private var mUnbinder: Unbinder? = null

    @get:LayoutRes
    protected abstract val layoutResource: Int

    protected abstract val toolBar: Toolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        mUnbinder = ButterKnife.bind(this)

        //setup toolbar
        val toolBar = toolBar
        if (toolBar != null) {
            setSupportActionBar(toolBar)
            toolBar.setNavigationIcon(R.drawable.ic_nav_back)
            toolBar.setNavigationOnClickListener {
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
        mUnbinder!!.unbind()
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
