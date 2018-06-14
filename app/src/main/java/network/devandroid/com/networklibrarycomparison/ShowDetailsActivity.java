package network.devandroid.com.networklibrarycomparison;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager;
import network.devandroid.com.networklibrarycomparison.internal.NetFactory;
import network.devandroid.com.networklibrarycomparison.internal.NetType;
import network.devandroid.com.networklibrarycomparison.utils.Validations;

public class ShowDetailsActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.details_tview)
    TextView mDetailsTview;

    @BindView(R.id.url_etext)
    TextInputEditText mUrlEtView;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ShowDetailsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrlEtView.setText(ICommonConstants.URL);

        mDetailsTview.setMovementMethod(new ScrollingMovementMethod());
        mUrlEtView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendRequest();
                    return true;
                }
                return false;
            }
        });
        sendRequest();

    }

    private void sendRequest() {
        final String url = mUrlEtView.getText().toString();
        if (Validations.isValidUrl(url)) {
            mProgressBar.setVisibility(View.VISIBLE);
            NetFactory.getNetworkManager(this, NetType.VOLLEY, new INetworkManager.Callback() {
                @Override
                public void onResponse(Object response) {
                    if (isFinishing()) {
                        return;
                    }
                    mDetailsTview.setText(response.toString());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Object error) {
                    if (isFinishing()) {
                        return;
                    }
                    mDetailsTview.setText(error.toString());
                    mProgressBar.setVisibility(View.GONE);
                }
            }).send(url);
        } else {
            Toast.makeText(this, R.string.err_invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_show_details;
    }

    @Override
    protected Toolbar getToolBar() {
        return mToolBar;
    }

}
