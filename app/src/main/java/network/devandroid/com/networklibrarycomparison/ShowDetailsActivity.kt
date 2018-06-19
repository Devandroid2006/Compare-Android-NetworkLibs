package network.devandroid.com.networklibrarycomparison

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import butterknife.BindView
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager
import network.devandroid.com.networklibrarycomparison.internal.NetFactory
import network.devandroid.com.networklibrarycomparison.internal.NetType
import network.devandroid.com.networklibrarycomparison.utils.Validations

class ShowDetailsActivity : BaseActivity() {

    @BindView(R.id.toolBar)
    protected override var toolBar: Toolbar? = null
        internal set

    @BindView(R.id.progressBar)
    internal var mProgressBar: ProgressBar? = null

    @BindView(R.id.details_tview)
    internal var mDetailsTview: TextView? = null

    @BindView(R.id.url_etext)
    internal var mUrlEtView: TextInputEditText? = null

    protected override val layoutResource: Int
        get() = R.layout.activity_show_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrlEtView!!.setText(ICommonConstants.URL)

        mDetailsTview!!.movementMethod = ScrollingMovementMethod()
        mUrlEtView!!.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendRequest()
                return@OnEditorActionListener true
            }
            false
        })
        sendRequest()

    }

    private fun sendRequest() {
        val url = mUrlEtView!!.text.toString()
        if (Validations.isValidUrl(url)) {
            mProgressBar!!.visibility = View.VISIBLE
            NetFactory.getNetworkManager(this, NetType.VOLLEY, object : INetworkManager.Callback {
                override fun onResponse(response: Any) {
                    if (isFinishing) {
                        return
                    }
                    mDetailsTview!!.text = response.toString()
                    mProgressBar!!.visibility = View.GONE
                }

                override fun onError(error: Any) {
                    if (isFinishing) {
                        return
                    }
                    mDetailsTview!!.text = error.toString()
                    mProgressBar!!.visibility = View.GONE
                }
            })!!.send(url)
        } else {
            Toast.makeText(this, R.string.err_invalid_url, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun launch(activity: Activity) {
            val intent = Intent(activity, ShowDetailsActivity::class.java)
            activity.startActivity(intent)
        }
    }

}
