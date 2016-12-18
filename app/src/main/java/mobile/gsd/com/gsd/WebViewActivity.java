package mobile.gsd.com.gsd;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Method;

/**
 * Created by ry on 8/31/16.
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6593943218195718/7861137189");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        View main = findViewById(R.id.main);
        webView = (WebView) findViewById(R.id.url_window);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (main != null) {
                main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        initWebView(webView);
        webView.loadUrl("http://gocalsd.weebly.com/");
        webView.setWebViewClient(new WebViewClient());

        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_refresh);
        activity.getSupportActionBar().setTitle("");

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                webView.reload();
                Toast.makeText(getApplicationContext(), "Refreshing",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.back:
                webView.goBack();
                return true;
            case R.id.forward:
                webView.goForward();
                return true;
            case R.id.stop:
                webView.stopLoading();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private final static Object methodInvoke(Object obj, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method m = obj.getClass().getMethod(method, new Class[] { boolean.class });
            m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initWebView(final WebView webView) {

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        // settings.setPluginsEnabled(true);
        methodInvoke(settings, "setPluginsEnabled", new Class[]{boolean.class}, new Object[]{true});
        // settings.setPluginState(PluginState.ON);
        methodInvoke(settings, "setPluginState", new Class[]{WebSettings.PluginState.class}, new Object[]{WebSettings.PluginState.ON});
        // settings.setPluginsEnabled(true);
        methodInvoke(settings, "setPluginsEnabled", new Class[]{boolean.class}, new Object[]{true});
        // settings.setAllowUniversalAccessFromFileURLs(true);
        methodInvoke(settings, "setAllowUniversalAccessFromFileURLs", new Class[]{boolean.class}, new Object[]{true});
        // settings.setAllowFileAccessFromFileURLs(true);
        methodInvoke(settings, "setAllowFileAccessFromFileURLs", new Class[]{boolean.class}, new Object[]{true});

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
    }
}