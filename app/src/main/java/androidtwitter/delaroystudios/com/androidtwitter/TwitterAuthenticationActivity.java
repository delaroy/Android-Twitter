package androidtwitter.delaroystudios.com.androidtwitter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterAuthenticationActivity extends Activity {
    public final static String EXTRA_URL = "extra_url";
    private static final String TAG = TwitterAuthenticationActivity.class
            .getSimpleName();
    private WebView mWebView = null;
    private ProgressDialog mDialog = null;
    private Activity mActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.authenticate_webview);
        mWebView = (WebView) findViewById(R.id.webview);
        final String url = this.getIntent().getStringExtra(EXTRA_URL);
        if (null == url) {
            finish();
        }
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);
    }

    @Override
    protected void onStop() {
        cancelProgressDialog();
        super.onStop();
    }

    @Override
    protected void onPause() {
        cancelProgressDialog();
        super.onPause();
    }

    private void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog.cancel();
            mDialog = null;
        }
    }

    @Override
    protected void onResume() {
        this.onRestart();
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            } catch (Exception exception) {
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mDialog == null)
                mDialog = new ProgressDialog(TwitterAuthenticationActivity.this);
            mDialog.setMessage("Loading..");

            if (!(mActivity.isFinishing())) {
                mDialog.show();
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.i(TAG, "Loading Resources");
            Log.i(TAG,
                    "Resource Loading Progress : " + view.getProgress());
            if (view.getProgress() >= 70) {
                cancelProgressDialog();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String verifier = uri.getQueryParameter("oauth_verifier");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("oauth_verifier", verifier);
            setResult(RESULT_OK, resultIntent);
            finish();
            return true;

        }
    }
}

